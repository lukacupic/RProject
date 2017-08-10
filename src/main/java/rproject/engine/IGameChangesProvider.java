package rproject.engine;

public interface IGameChangesProvider {

	void addGameListener(IGameChangesListener listener);

	void removeGameListener(IGameChangesListener listener);

	void fire();

}