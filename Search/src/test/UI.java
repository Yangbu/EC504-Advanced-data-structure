package test;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class UI extends JFrame implements ActionListener, ItemListener {

	public static final int X = 200;
	public static final int Y = 10;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 650;

	// one JTextField
	JTextField jtf = new JTextField();

	// three jbuttons
	JButton jbt1 = new JButton("Index");
	JButton jbt2 = new JButton("Search");
	Checkbox box = new Checkbox("ShowDetails");

	String fileName = "";

	JList<String> list;

	Border border = new TitledBorder(new EtchedBorder());

	JScrollPane scroll;

	JPanel down;
	boolean flag;

	List<String> results = new LinkedList<String>();

	public String search;

	InvertedIndex index;

	public UI() {
		this.index = new InvertedIndex();
		init();
	}

	public void init() {

		this.setBounds(X, Y, WIDTH, HEIGHT);
		this.setVisible(true);

		// the left is divided into 1 row and 2 cols
		GridLayout lay = new GridLayout(0, 2);

		// the right is divided into 1 row and 3 cols
		GridLayout out = new GridLayout(0, 3);
		out.setHgap(16);

		JPanel PanelUp = new JPanel(new GridLayout());
		PanelUp.setBorder(border);

		// add the panel to north
		this.getContentPane().add(PanelUp, BorderLayout.NORTH);

		JPanel up = new JPanel(lay);

		JPanel upLeft = new JPanel(new GridLayout());
		upLeft.add(jtf);

		JPanel upRight = new JPanel(out);

		upRight.add(jbt2);
		jbt2.addActionListener(this);
		getRootPane().setDefaultButton(jbt2);

		upRight.add(box);
		box.addItemListener(this);

		upRight.add(jbt1);
		jbt1.addActionListener(this);


		up.add(upLeft);
		up.add(upRight);

		PanelUp.add(up);

		this.validate();

		down = new JPanel();

		// add the panel to center
		this.add(down, BorderLayout.CENTER);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// get the fileName
		if (e.getActionCommand().equalsIgnoreCase("Index")) {
			JFileChooser chooser = new JFileChooser();

			int returnVal = chooser.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fileName = chooser.getSelectedFile().getAbsolutePath();
				if (fileName.equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(this, "Please choose a file");
				} else {
					if (fileName.toLowerCase().endsWith(".txt")) {
						try {
							new Parser(index, fileName, null);
							JOptionPane.showMessageDialog(this,
									"Index completed!");
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(this, "Index error:"
									+ e1.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(this,
								"choose a correct file");
					}
				}

			}

		} else if (e.getActionCommand().equalsIgnoreCase("Search")) {
			String sear = this.jtf.getText().trim();
			if (sear.equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(this,
						"Input keywords!");
			} else {

				// get the information that you want to search
				this.search = sear;
				query();
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// get the state of checkbox
		if (e.getStateChange() == ItemEvent.SELECTED) {
			flag = true;
			this.search = jtf.getText().trim();
		} else {
			flag = false;
			this.search = jtf.getText().trim();
		}
	}

	public void query() {
		this.down.removeAll();

		Query query = new Query(index);

		DefaultListModel<String> model = new DefaultListModel<String>();
		list = new JList<String>(model);
		scroll = new JScrollPane(list);

		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		list.setLayoutOrientation(JList.VERTICAL_WRAP);

		down.setLayout(new GridLayout());

		// if (results.size() != 0) {
		// results.clear();
		// }

		results = query.query(search, flag);

		int num = this.results.size();

		GridLayout layout = new GridLayout(11, 0);
		layout.setVgap(5);
		list.setLayout(layout);
		list.setFont(new Font("Courier", Font.PLAIN, 18));

		if (num != 0) {

			for (int i = 0; i < num; i++) {

				String str = this.results.get(i);

				model.addElement(str);
			}
			down.add(scroll);

			down.setBorder(border);
		} else {
			down.add(scroll);

			down.setBorder(border);
			JOptionPane.showMessageDialog(this, "Not found!");
		}

		// refresh
		this.validate();

	}

	public static void main(String[] args) {
		new UI();
	}

}
