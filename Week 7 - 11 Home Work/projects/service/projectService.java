package projects.service;

import java.util.List;
import java.util.NoSuchElementException;

import projects.dao.projectDao;
import projects.entity.Project;
import projects.exception.DbE;

public class projectService {

	private projectDao pd = new projectDao();
	
	public Project addProject(Project p) {
		return pd.addProject(p);
	}

	public List<Project> getProjects() {
		return pd.getProjects();
	}

	public Project getProjectById(Integer id) {
		return pd.getProjectById(id).orElseThrow( () -> new NoSuchElementException( "Project with id '" + id + "' does not exist" ) );
	}

	public void modifyProject(Project p) {
		if(!pd.modifyProject(p)) {
			throw new DbE("Project with id '" + p.getProjectId() + "' does not exist");
		}
	}

	public void deleteProject(Integer id) {
		if(!pd.deleteProject(id)) {
			throw new DbE("Project with id '" + id + "' does not exist");
		}
		
	}

}
