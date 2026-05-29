package visual.panels;

import models.Claim;
import models.Client;
import models.Policy;
import services.ClaimServices;
import services.ClientServices;
import services.PolicyServices;
import visual.UIStyles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static java.awt.Font.*;

public class ReportsPanel extends JPanel {

    private Timer timer;
    private TimerTask timerTask;
    private  List<Client> clients;
    private List<Policy> policies;
    private  List<Claim> claims;
    private  long activePolicies;
    private long openClaims;
    private  double totalClaimed;
    private double totalCompensated;
    private JPanel metricsGrid;
    private Dimension screenSize;
    private JDesktopPane jDesktopPane;
    private ArrayList<JLabel> metrics;
    private ArrayList<JLabel> calculations;
    private JButton next;
    private JButton previus;
    private int currentPage;
    private  ReportsPanel1 p1;
    private  ReportsPanel2 p2;
    private  ReportsPanel3 p3;
    private  ReportsPanel4 p4;
    private JPanel summaryCard;
    public ReportsPanel() {
        metrics = new ArrayList<>();
        calculations = new ArrayList<>();
        next = new JButton(">");
        previus = new JButton("<");

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBackground(UIStyles.BG_LIGHT);
        setBounds(0, 0, screenSize.width, (int) (screenSize.height*0.94));
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel container = new JPanel(null);
        container.setBounds((int) (screenSize.width*0.025), (int) (screenSize.height*0.1),  (int) (screenSize.width*0.95), (int) (screenSize.height*0.795));
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(10, 10, 10, 10));


        JLabel title = new JLabel("Reportes del Sistema");
        title.setFont(UIStyles.FONT_HEADER);
        title.setForeground(UIStyles.TEXT_PRIMARY);
        title.setBounds((int) (screenSize.width*0.02), (int) (screenSize.height*0.04), (int) (screenSize.width*0.15), (int) (screenSize.height*0.06));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(title);

        metricsGrid = new JPanel(null);
        metricsGrid.setBounds(0, 0, (int) (screenSize.width*0.95), (int) (screenSize.height*0.335));
        metricsGrid.setOpaque(false);

        createMetrics();

        metricsGrid.add(createMetricCard("Total Clientes", String.valueOf(clients.size()), UIStyles.CARD_BLUE, 0, 0) );
        metricsGrid.add(createMetricCard("Total P\u00F3lizas", String.valueOf(policies.size()), UIStyles.CARD_GREEN, 1,0));
        metricsGrid.add(createMetricCard("Total Reclamos", String.valueOf(claims.size()), UIStyles.CARD_ORANGE, 2,0));
        metricsGrid.add(createMetricCard("P\u00F3lizas Vigentes", String.valueOf(activePolicies), UIStyles.CARD_TEAL, 0, 1));
        metricsGrid.add(createMetricCard("Reclamos Abiertos", String.valueOf(openClaims), UIStyles.CARD_RED,1,1));
        metricsGrid.add(createMetricCard("Monto Total Compensado", String.format("$%.2f", totalCompensated), UIStyles.CARD_PURPLE,2,1));

        //JDesktopPane creacion
        jDesktopPane = new JDesktopPane();
        jDesktopPane.setOpaque(false);
        jDesktopPane.setBounds((int) (screenSize.width*0.03), (int) (screenSize.height*0.345),  (int) (screenSize.width*0.89), (int) (screenSize.height*0.425));

        summaryCard = UIStyles.createCard();
        summaryCard.setBounds(0, 0,  (int) (screenSize.width*0.89), (int) (screenSize.height*0.425));
        summaryCard.setLayout(null);
        summaryCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));

        JLabel summaryTitle = new JLabel("Resumen General");
        summaryTitle.setFont(new Font("Segoe UI", BOLD, (int) (screenSize.width*0.0135)));
        summaryTitle.setHorizontalAlignment(SwingConstants.CENTER);
        summaryTitle.setBounds((int) (screenSize.width*0.35), (int) (screenSize.height*0.05), (int) (screenSize.width*0.19), (int) (screenSize.height*0.05));
        summaryTitle.setForeground(UIStyles.TEXT_PRIMARY);
        summaryCard.add(summaryTitle);

        summaryCard.add(createSummaryLine("Total reclamado:    ", String.format("$%.2f", totalClaimed), 0));
        summaryCard.add(createSummaryLine("Total compensado:   ", String.format("$%.2f", totalCompensated), 1));
        summaryCard.add(createSummaryLine("Diferencia:            ", String.format("$%.2f", totalClaimed - totalCompensated), 2));

        jDesktopPane.add(summaryCard, 0);

        p1 = new ReportsPanel1();
        jDesktopPane.add(p1, 1);
        p2= new ReportsPanel2();
        jDesktopPane.add(p2, 2);
        p3= new ReportsPanel3();
        jDesktopPane.add(p3, 3);
        p4= new ReportsPanel4();
        jDesktopPane.add(p4, 4);
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
        p4.setVisible(false);

        summaryCard.setVisible(true);

        next.setBounds((int) (screenSize.width * 0.895), (int) (screenSize.height * 0.53), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.08));
        next.setFocusPainted(false);
        next.setHorizontalAlignment(SwingConstants.RIGHT);
        next.setBorderPainted(false);
        next.setFocusPainted(false);
        next.setContentAreaFilled(false);
        next.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                nextMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                nextMouseExited(evt);
            }
        });
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });
        next.setFont(new Font("Segoe UI Emoji", PLAIN, (int) (screenSize.width * 0.04)));

        container.add(next);


        previus.setBounds((int) (screenSize.width * -0.015), (int) (screenSize.height * 0.53), (int) (screenSize.width * 0.07), (int) (screenSize.height * 0.08));
        previus.setFocusPainted(false);
        previus.setHorizontalAlignment(SwingConstants.LEFT);
        previus.setBorderPainted(false);
        previus.setFocusPainted(false);
        previus.setContentAreaFilled(false);
        previus.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                previusMouseEntered(evt);
            }

            public void mouseExited(MouseEvent evt) {
                previusMouseExited(evt);
            }
        });
        previus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                previusActionPerformed(evt);
            }
        });
        previus.setFont(new Font("Segoe UI Emoji", PLAIN, (int) (screenSize.width * 0.04)));

        container.add(previus);



        container.add(metricsGrid);
        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));


        timer = new Timer();
        timerTask= new TimerTask() {
            @Override
            public void run() {
                createMetrics();
                fixData();
                if (currentPage == 0)
                    previus.setEnabled(false);

                else
                    previus.setEnabled(true);
                if (currentPage == jDesktopPane.getComponentCount() - 1)
                    next.setEnabled(false);
                else
                    next.setEnabled(true);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 500);

        currentPage=0;
        container.add(jDesktopPane);
        add(container);

        InputMap inputMapN = next.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMapN = next.getActionMap();

        KeyStroke keyStrokeN = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);

        inputMapN.put(keyStrokeN, "activateNBut");
        actionMapN.put("activateNBut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next.doClick();
            }
        });

        InputMap inputMapP = previus.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap previusActionMap = previus.getActionMap();

        KeyStroke keyStrokeP = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);

        inputMapP.put(keyStrokeP, "activatePBut");
        previusActionMap.put("activatePBut", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previus.doClick();
            }
        });


    }

    private void fixData() {
        metrics.get(0).setText(String.valueOf(clients.size()));
        metrics.get(1).setText(String.valueOf(policies.size()));
        metrics.get(2).setText(String.valueOf(claims.size()));
        metrics.get(3).setText(String.valueOf(activePolicies));
        metrics.get(4).setText(String.valueOf(openClaims));
        metrics.get(5).setText(String.valueOf(totalClaimed));

        calculations.get(0).setText("<html><b>" + "Total reclamado:    " + "</b>" + String.format("$%.2f", totalClaimed) + "</html>");
        calculations.get(1).setText("<html><b>" + "Total compensado:   " + "</b>" + String.format("$%.2f", totalCompensated) + "</html>");
        calculations.get(2).setText("<html><b>" + "Diferencia:            " + "</b>" + String.format("$%.2f", totalClaimed - totalCompensated) + "</html>");
    }


    private void createMetrics() {
        clients = new ClientServices().getAllClients();
        policies  = new PolicyServices().getAllPolicies();
        claims = new ClaimServices().getAllClaims();

        activePolicies = policies.stream().filter(p -> {
             try { return p.getPolicyStatusId() == 1; } catch (Exception e) { return false; }
         }).count();

        openClaims = claims.stream().filter(c -> {
             try { return c.getClaimStatusId() == 1; } catch (Exception e) { return false; }
         }).count();

        totalClaimed= claims.stream().mapToDouble(Claim::getClaimedAmount).sum();
        totalCompensated = claims.stream().mapToDouble(Claim::getCompensatedAmount).sum();

         }

    private JPanel createMetricCard(String label, String value, Color accent, int largo, int ancho) {
        JPanel card = new JPanel(null);
        card.setBackground(UIStyles.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIStyles.BORDER, 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(3, 0, 0, 0, accent),
                        BorderFactory.createEmptyBorder(22, 22, 22, 22))));

        card.setBounds((int) (screenSize.width*0.32)*largo, (int) (screenSize.height*0.174)*ancho, (int) (screenSize.width*0.31), (int) (screenSize.height*0.156));

        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Segoe UI", BOLD, (int) (screenSize.width*0.017)));
        valLabel.setForeground(accent);
        valLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valLabel.setBounds((int) (screenSize.width*0.08), (int) (screenSize.height*0.04), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));
        metrics.add(valLabel);
        card.add(valLabel);

        JLabel descLabel = new JLabel(label);
        descLabel.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width*0.0115)));
        descLabel.setForeground(UIStyles.TEXT_SECONDARY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descLabel.setBounds((int) (screenSize.width*0.08), (int) (screenSize.height*0.09), (int) (screenSize.width*0.15), (int) (screenSize.height*0.04));
        card.add(descLabel);

        return card;
    }

    private JLabel createSummaryLine(String label, String value, int i) {
        JLabel lbl = new JLabel("<html><b>" + label + "</b>" + value + "</html>");
        lbl.setFont(new Font("Segoe UI", PLAIN, (int) (screenSize.width*0.0112)));
        lbl.setForeground(UIStyles.TEXT_PRIMARY);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setBounds((int) (screenSize.width*0.25), (int) (((screenSize.height*0.05)*i)+ screenSize.height*0.15), (int) (screenSize.width*0.39), (int) (screenSize.height*0.05));
        calculations.add(lbl);
        return lbl;
    }

    public void changeJDesk(int i) {
        switch (currentPage) {
            case 0:
                summaryCard.setVisible(false);
                break;
            case 1:
                p1.setVisible(false);
                break;
            case 2:
                p2.setVisible(false);
                break;
            case 3:
                p3.setVisible(false);
                break;
            case 4:
                p4.setVisible(false);
                break;
            default:
                break;
        }

        switch (i) {
            case 0:
                summaryCard.setVisible(true);
                break;
            case 1:
                p1.setVisible(true);
                break;
            case 2:
                p2.setVisible(true);
                break;
            case 3:
                p3.setVisible(true);
                break;
            case 4:
                p4.setVisible(true);
                break;
            default:
                break;
        }
        if ((i >= 0 && i <= jDesktopPane.getComponentCount()))
            currentPage = i;

    }

    private void previusActionPerformed(ActionEvent evt) {
        if (currentPage > 0)
            changeJDesk(currentPage - 1);
    }

    private void previusMouseExited(MouseEvent evt) {
        if (previus.isEnabled()) {
           previus.setForeground(Color.black);
        }
    }

    private void previusMouseEntered(MouseEvent evt) {
        if(previus.isEnabled()) {
            previus.setForeground(Color.RED);
        }
    }

    private void nextActionPerformed(ActionEvent evt) {
        if((currentPage<jDesktopPane.getComponentCount()-1)){
            changeJDesk(currentPage+1);
        }
    }

    private void nextMouseExited(MouseEvent evt) {
        if(next.isEnabled()) {
           next.setForeground(Color.BLACK);
        }
    }

    private void nextMouseEntered(MouseEvent evt) {
        if(next.isEnabled()) {
           next.setForeground(Color.red);
        }
    }
}
