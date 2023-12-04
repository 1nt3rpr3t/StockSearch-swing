import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputStock;
	private JLabel result;

	public static Elements[] Stock(String stockSymbol) {
		Elements[] moneyT = new Elements[2];
		try {
			String url = "https://www.google.com/search?q=" + stockSymbol + "+주가";
			Document doc = Jsoup.connect(url).get();

			Elements money = doc.getElementsByAttributeValue("jsname", "vWLAgc");
			Elements moneyType = doc.getElementsByAttributeValue("jsname", "T3Us2d");

			moneyT[0] = money;
			moneyT[1] = moneyType;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "검색오류, 인터넷연결상태를 확인하고 다시 실행시켜주세요", "경고", JOptionPane.ERROR_MESSAGE);
		}
		return moneyT;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockFrame frame = new StockFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StockFrame() {
		setTitle("JavaStockSearcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel information = new JLabel("검색하고 싶은 주식을 입력하세요");
		information.setHorizontalAlignment(SwingConstants.CENTER);
		information.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		information.setBounds(49, 40, 302, 50);
		contentPane.add(information);

		inputStock = new JTextField();
		inputStock.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		inputStock.setHorizontalAlignment(SwingConstants.CENTER);
		inputStock.setBounds(100, 94, 200, 50);
		contentPane.add(inputStock);

		JButton sarechButton = new JButton("확인");
		sarechButton.setBounds(150, 155, 100, 33);
		contentPane.add(sarechButton);

		result = new JLabel("");
		result.setHorizontalAlignment(SwingConstants.CENTER);
		result.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		result.setBounds(49, 198, 302, 93);
		contentPane.add(result);

		sarechButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stockSymbol = inputStock.getText();
				Elements[] moneyT = Stock(stockSymbol);

				if (moneyT[0].isEmpty() || moneyT[1].isEmpty()) {
					result.setText("<html><div style='text-align: center;'>" + stockSymbol
							+ "이라는 주식은 현재<br>검색되지 않습니다</div></html>");
				} else {
					result.setText("<html><div style='text-align: center;'>" + stockSymbol + "의 주식은 현재<br>"
							+ moneyT[0].first().text() + " " + moneyT[1].first().text() + " 입니다</div></html>");
				}
			}
		});
	}
}
