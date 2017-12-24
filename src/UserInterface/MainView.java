package UserInterface;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import IO.DrawPanel;
import IO.LoadFile;
import IO.SaveFile;
import Program.MainMethod;
import Values.BestChromosome;
import Values.Data;
import Values.DataSet;
import Values.Parameter;

public class MainView extends JFrame {
	
	/**
	 * M10456012
	 * Kevin Yen
	 * kelly10056040@gmail.com
	 */
	private static final long serialVersionUID = 1L;
	private boolean isLoad = false;
	private String dataSetName = "";
	private BestChromosome bestChromosome = null;
	private DrawPanel drawPanel = new DrawPanel();
	private DataSet dataSet = DataSet.getInstance();
	
	//*****宣告介面*****//
	Container cp = this.getContentPane();
	
	//*****宣告元件*****//
	JLabel generationLabel = new JLabel("Generation:");
	JLabel chromosomeLabel = new JLabel("Chromosome:");
	JLabel crossoverRateLabel = new JLabel("Crossover Rate:");
	JLabel mutationRateLabel = new JLabel("Mutation Rate:");
	JLabel dataSetLabel = new JLabel("DateSet: ");
	JLabel runTimeLabel = new JLabel("Running Time: ");
	JLabel distanceLabel = new JLabel("Best Distance: ");
	JLabel fitnessLabel = new JLabel("Best Fitness: ");
	JLabel avgRunTimeLabel = new JLabel("Avg Time: ");
	JLabel avgDistanceLabel = new JLabel("Avg Distance: ");
	
	JTextField generationTextField = new JTextField("50");
	JTextField chromosomeTextField = new JTextField("40");
	JTextField crossoverRateTextField = new JTextField("0.8");
	JTextField mutationRateTextField = new JTextField("0.2");
	JTextField computerRunTextField = new JTextField("30");
	
	JButton loadFileButton = new JButton("Load File");
	JButton startButton = new JButton("Starts");
	JButton drawButton = new JButton("Draw");
	
	JCheckBox isMacCheckBox = new JCheckBox("Using Mac");
	JCheckBox isInteger = new JCheckBox("Output Integer");
	JCheckBox isComputerRunCheckBox = new JCheckBox("Computer Run");
	JTextArea output = new JTextArea();
	JPanel show = new JPanel();
	JFileChooser open = new JFileChooser();
	JScrollPane sp = new JScrollPane(output,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	MainView()
	{
		//*****設定介面*****//
		this.setSize(1000, 800);
		this.setLayout(null);
		this.setTitle("Genetic Algorithm");
		
		//*****設定元件位置*****//
		generationLabel.setBounds(700, 30, 100, 30);
		generationTextField.setBounds(800, 30, 100, 30);
		chromosomeLabel.setBounds(700, 70, 100, 30);
		chromosomeTextField.setBounds(800, 70, 100, 30);
		crossoverRateLabel.setBounds(700, 110, 100, 30);
		crossoverRateTextField.setBounds(800, 110, 100, 30);
		mutationRateLabel.setBounds(700, 150, 150, 30);
		mutationRateTextField.setBounds(800, 150, 100, 30);
		
		isMacCheckBox.setBounds(700, 220, 120, 30);
		isInteger.setBounds(820, 220, 150, 30);
		loadFileButton.setBounds(700, 250, 200, 30);
		startButton.setBounds(700, 290, 200, 30);
		drawButton.setBounds(700, 330, 200, 30);
		isComputerRunCheckBox.setBounds(700, 370, 150, 30);
		computerRunTextField.setBounds(850, 370, 40, 30);
		dataSetLabel.setBounds(700, 400, 250, 30);
		runTimeLabel.setBounds(700, 420, 250, 30);
		distanceLabel.setBounds(700, 440, 250, 30);
		fitnessLabel.setBounds(700, 460, 250, 30);
		avgRunTimeLabel.setBounds(700, 480, 250, 30);
		avgDistanceLabel.setBounds(700, 500, 250, 30);
		
		show.setBounds(20, 20, 650, 500);
		output.setBounds(20, 530, 950, 200);
		sp.setBounds(20, 530, 950, 200);
		
		//*****設定JPanel邊框*****//
		show.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		output.setEditable(false);//JTextArea不可編輯
		output.setLineWrap(true);//JTextArea自動換行
		
		cp.add(generationLabel);
		cp.add(generationTextField);
		cp.add(chromosomeLabel);
		cp.add(chromosomeTextField);
		cp.add(crossoverRateLabel);
		cp.add(crossoverRateTextField);
		cp.add(mutationRateLabel);
		cp.add(mutationRateTextField);
		cp.add(startButton);
		cp.add(loadFileButton);
		cp.add(show);
		cp.add(sp);
		cp.add(runTimeLabel);
		cp.add(distanceLabel);
		cp.add(fitnessLabel);
		cp.add(isMacCheckBox);
		cp.add(isComputerRunCheckBox);
		cp.add(computerRunTextField);
		cp.add(avgRunTimeLabel);
		cp.add(avgDistanceLabel);
		cp.add(isInteger);
		cp.add(drawButton);
		cp.add(dataSetLabel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		loadFileButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				LoadFile loadFile = new LoadFile();
				String tmpStr = loadFile.loadfile(open, show, isMacCheckBox.isSelected());
				if (!tmpStr.equals("")) {
					dataSetName = tmpStr;
				}
				dataSetLabel.setText("DateSet: " + dataSetName);
				if (!dataSetLabel.getText().equals("DateSet: "))
					isLoad = true;
			}});
		
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoadFile loadFile = new LoadFile();
				SaveFile saveFile = new SaveFile();
				if (isLoad) {
					
					if (dataSet.getIsAuto()) {
						for (int i=0; i<dataSet.getCount(); i++) {
							loadFile.autoLoadFile(show, isMacCheckBox.isSelected(), i);
							computerRunAlgorithm(isInteger.isSelected(),0);
							saveFile.saveFile(dataSet.getDataSet(i), bestChromosome, isMacCheckBox.isSelected());
						}
					} else {
						initialOutPut();
						if (isComputerRunCheckBox.isSelected()) {
							computerRunAlgorithm(isInteger.isSelected(), 0);
						}
						else
							runAlgorithm(isInteger.isSelected());
					}
				}
			}});
		drawButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (bestChromosome != null)
					showResult(bestChromosome, output);
			}});
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainView();
	}

	private void runAlgorithm(boolean isInteger) {
		// 記錄開始時間
		long startTime = System.currentTimeMillis();
		// 設定參數
		setParameter(0);
		// 執行GA
		MainMethod main = new MainMethod();
		this.bestChromosome = main.mainProgram(isInteger);
		// 記錄結束時間
		long endTime = System.currentTimeMillis();
		runTimeLabel.setText("Running Time: " + getRunTime(startTime, endTime) + " s");
		showResult(this.bestChromosome, output);
	}
	
	private void computerRunAlgorithm(boolean isInteger, int count) {
		int runTime = Integer.parseInt(computerRunTextField.getText());
		long startTime = 0;
		long endTime = 0;
		double totalDistance = 0;
		double totalTime = 0;
		ArrayList<Integer> bestPath = null;
		double bestDistance = -1;
		double bestTime = 0;
		for (int i=0; i<runTime; i++) {
			// 記錄開始時間
			startTime = System.currentTimeMillis();
			// 設定參數
			setParameter(count);
			// 執行GA
			MainMethod main = new MainMethod();
			this.bestChromosome = main.mainProgram(isInteger);
			// 記錄結束時間
			endTime = System.currentTimeMillis();
			// 記錄最佳
			if (i==0) {
				bestPath = this.bestChromosome.chromosome;
				bestDistance = this.bestChromosome.distance;
				bestTime = getRunTime(startTime, endTime);
			} else {
				if (bestDistance > this.bestChromosome.distance) {
					bestPath = this.bestChromosome.chromosome;
					bestDistance = this.bestChromosome.distance;
					bestTime = getRunTime(startTime, endTime);
				}
			}
			totalDistance += bestChromosome.distance;
			totalTime += getRunTime(startTime, endTime);
		}
		this.bestChromosome = new BestChromosome();
		this.bestChromosome.chromosome = bestPath;
		this.bestChromosome.distance = bestDistance;
		this.bestChromosome.fitness = 1/bestDistance;
		this.bestChromosome.time = bestTime;
		this.bestChromosome.avgDistance = totalDistance/runTime;
		this.bestChromosome.avgTime = totalTime/runTime;
		showResult(this.bestChromosome, this.output);
		this.runTimeLabel.setText("Running Time: " + bestTime + " s");
		this.avgRunTimeLabel.setText("Avg Time: " + totalTime/runTime + " s");
		this.avgDistanceLabel.setText("Avg Distance: " + totalDistance/runTime);
	}
	
	private void setParameter(int count) {
		Parameter parameter = Parameter.getInstance();
		if (dataSet.getIsAuto()) {
			Data data = Data.getInstance();
			int generation = (data.total-(data.total%50)+50)*10;
			int chromosome = 50;
			if (data.total > 1000) {
				generation = 500;
				chromosome = 100;
			} else {
				generation = (data.total+(data.total%50))*10;
				chromosome = data.total;
			}
			parameter.setGeneration(generation);
			parameter.setChromosome(chromosome);
			parameter.setCossoverRate(Double.parseDouble(crossoverRateTextField.getText()));
			parameter.setMutationRate(Double.parseDouble(mutationRateTextField.getText()));
		} else {
			parameter.setGeneration(Integer.parseInt(generationTextField.getText()));
			parameter.setChromosome(Integer.parseInt(chromosomeTextField.getText()));
			parameter.setCossoverRate(Double.parseDouble(crossoverRateTextField.getText()));
			parameter.setMutationRate(Double.parseDouble(mutationRateTextField.getText()));
		}
	}
	
	private void initialOutPut() {
		runTimeLabel.setText("Run Time: ");
		distanceLabel.setText("Best Distance: ");
		fitnessLabel.setText("Best Fitness: ");
		avgRunTimeLabel.setText("Avg Time: ");
		avgDistanceLabel.setText("Avg Distance: ");
	}

	private void showResult(BestChromosome bestChromosome, JTextArea output) {
		// 清空畫布
		this.drawPanel.drawpanel(show);
		int size = bestChromosome.chromosome.size();
		String out = this.dataSetLabel.getText() + "\nBest Path: \n";
		for (int i=0; i<size; i++) {
			if (i==size-1) {
				this.drawPanel.drawline(bestChromosome.chromosome.get(i), bestChromosome.chromosome.get(0), show);
				out += bestChromosome.chromosome.get(i) + " - " + bestChromosome.chromosome.get(0) + "\n";
			} else {
				this.drawPanel.drawline(bestChromosome.chromosome.get(i), bestChromosome.chromosome.get(i+1), show);
				out += bestChromosome.chromosome.get(i) + " - ";
			}
		}
		out += "Best Distance: " + bestChromosome.distance;
		this.distanceLabel.setText("Best Distance: " + bestChromosome.distance);
		this.fitnessLabel.setText("Best Fitness: " + bestChromosome.fitness);
		output.setText(out);
	}
	
	private double getRunTime(long start, long end) {
		double startTime = start;
		double endTime = end;
		return (endTime - startTime)/1000;
	}
}
