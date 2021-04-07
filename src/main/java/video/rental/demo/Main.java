package video.rental.demo;

import video.rental.demo.application.Interactor;
import video.rental.demo.domain.Repository;
import video.rental.demo.domain.TextReportImpl;
import video.rental.demo.infrastructure.RepositoryDBImpl;
import video.rental.demo.presentation.CmdUI;
import video.rental.demo.presentation.GraphicUI;
import video.rental.demo.utils.SampleGenerator;

public class Main {
	private static GraphicUI ui;
	private static Repository repository = new RepositoryDBImpl();
	public static void main(String[] args) {
		SampleGenerator sampleGenerator = new SampleGenerator(repository);
		sampleGenerator.generateSamples();
		Interactor interactor = new Interactor(repository, new TextReportImpl());
		
		ui = new GraphicUI(interactor);

		ui.start();
	}
}
