package video.rental.demo;

import video.rental.demo.application.Interactor;
import video.rental.demo.domain.Repository;
import video.rental.demo.infrastructure.RepositoryDBImpl;
import video.rental.demo.presentation.CmdUI;
import video.rental.demo.utils.SampleGenerator;

public class Main {
	private static CmdUI ui;

	public static void main(String[] args) {
		Repository repository = new RepositoryDBImpl();
		new SampleGenerator(repository).generateSamples();

		Interactor interactor = new Interactor(repository);

		ui = new CmdUI(interactor);
		ui.start();

//		GraphicUI gui = new GraphicUI();
//		gui.start();
	}
}
