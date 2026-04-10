import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import config.Database;

public class InventoryApp extends JFrame {
    private JTextField txtName, txtPrice, txtQty;
    private JTable table;
    private DefaultTableModel model;

    // Custom Colors for a "Gaming/Pro" MSI Aesthetic
    private final Color SIDEBAR_BG = new Color(35, 38, 45);
    private final Color MAIN_BG = new Color(24, 26, 31);
    private final Color ACCENT_BLUE = new Color(66, 135, 245);
    private final Color SUCCESS_GREEN = new Color(46, 204, 113);
    private final Color DANGER_RED = new Color(231, 76, 60);

    public InventoryApp() {
        setTitle("Inventory Management System | Gautam,Prachi,Ananya");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(MAIN_BG);
        setLayout(new BorderLayout());

        // --- 1. TOP NAVIGATION BAR ---
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(SIDEBAR_BG);
        navBar.setPreferredSize(new Dimension(100, 60));
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(50, 50, 60)));

        JLabel logo = new JLabel("  INVENTORY");
        logo.setFont(new Font("Inter", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        navBar.add(logo, BorderLayout.WEST);

        add(navBar, BorderLayout.NORTH);

        // --- 2. STYLED SIDEBAR (FORM AREA) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(320, 0));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        // Form Title
        JLabel formTitle = new JLabel("Product Details");
        formTitle.setFont(new Font("Inter", Font.BOLD, 16));
        formTitle.setForeground(ACCENT_BLUE);
        sidebar.add(formTitle);
        sidebar.add(Box.createVerticalStrut(25));

        // Styled Inputs
        txtName = createStyledField("Enter Product Name");
        txtPrice = createStyledField("Enter Price (INR)");
        txtQty = createStyledField("Enter Quantity");

        sidebar.add(createInputLabel("PRODUCT NAME"));
        sidebar.add(txtName);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(createInputLabel("UNIT PRICE"));
        sidebar.add(txtPrice);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(createInputLabel("QUANTITY IN STOCK"));
        sidebar.add(txtQty);
        sidebar.add(Box.createVerticalStrut(35));

        // Buttons with Hover Effect (Simplified for Basic Swing)
        JButton btnAdd = createStyledButton("ADD PRODUCT", SUCCESS_GREEN);
        btnAdd.addActionListener(e -> addProduct());
        sidebar.add(btnAdd);

        sidebar.add(Box.createVerticalStrut(12));

        JButton btnDelete = createStyledButton("DELETE ITEM", DANGER_RED);
        btnDelete.addActionListener(e -> deleteProduct());
        sidebar.add(btnDelete);

        add(sidebar, BorderLayout.WEST);

        // --- 3. MAIN CONTENT (TABLE AREA) ---
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(MAIN_BG);
        contentArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Table Setup
        model = new DefaultTableModel(new String[]{"REF ID", "PRODUCT NAME", "PRICE", "STOCK"}, 0);
        table = new JTable(model);
        styleTable(table);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder()); // Flat look
        sp.getViewport().setBackground(MAIN_BG);
        contentArea.add(sp, BorderLayout.CENTER);

        add(contentArea, BorderLayout.CENTER);

        loadData();
    }

    // --- CUSTOM UI HELPERS ---
    private JTextField createStyledField(String placeholder) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setBackground(new Color(45, 48, 56));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 70), 1),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        return field;
    }

    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.BOLD, 10));
        label.setForeground(new Color(150, 150, 160));
        return label;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setFont(new Font("Inter", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    private void styleTable(JTable t) {
        t.setRowHeight(45);
        t.setBackground(MAIN_BG);
        t.setForeground(new Color(200, 200, 210));
        t.setGridColor(new Color(40, 40, 50));
        t.setSelectionBackground(new Color(ACCENT_BLUE.getRed(), ACCENT_BLUE.getGreen(), ACCENT_BLUE.getBlue(), 50));
        
        JTableHeader header = t.getTableHeader();
        header.setBackground(SIDEBAR_BG);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Inter", Font.BOLD, 12));
        header.setBorder(BorderFactory.createEmptyBorder());
    }

    // --- DATABASE LOGIC (Same as before but with UI Refresh) ---
    private void loadData() {
        model.setRowCount(0);
        try (Connection con = Database.connect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM products")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    "GS-" + rs.getInt("id"),
                    rs.getString("p_name").toUpperCase(),
                    "₹ " + rs.getDouble("price"),
                    rs.getInt("quantity") + " Units"
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void addProduct() {
        try (Connection con = Database.connect();
             PreparedStatement pst = con.prepareStatement("INSERT INTO products (p_name, price, quantity) VALUES (?, ?, ?)")) {
            pst.setString(1, txtName.getText());
            pst.setDouble(2, Double.parseDouble(txtPrice.getText()));
            pst.setInt(3, Integer.parseInt(txtQty.getText()));
            pst.executeUpdate();
            txtName.setText(""); txtPrice.setText(""); txtQty.setText("");
            loadData();
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Check Data Format!"); }
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row != -1) {
            String idStr = model.getValueAt(row, 0).toString().replace("GS-", "");
            try (Connection con = Database.connect();
                 PreparedStatement pst = con.prepareStatement("DELETE FROM products WHERE id = ?")) {
                pst.setInt(1, Integer.parseInt(idStr));
                pst.executeUpdate();
                loadData();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        // Advanced Rounded Settings
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 12);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("Table.selectionArc", 8);

        FlatDarkLaf.setup();
        EventQueue.invokeLater(() -> new InventoryApp().setVisible(true));
    }
}