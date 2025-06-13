package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.service.projectService;

public class app {
	
	private static boolean done = false;
	
	private Scanner sc = new Scanner(System.in);
	private Project curProject;
	
	private projectService ps = new projectService();
	
	private List<String> ops = List.of (
			"1. Add a Project",
			"2. List Projects",
			"3. Select Project",
			"4. Update Project Details",
			"5. Delete Project"
			);

	public static void main(String[] args) {
		
		app app = new app();
		
		while(!done) {
			app.displayMenu();
		}

	}
	
	public void displayMenu() {
		ops.forEach(line -> System.out.println(line));
		
		try {
		
			Integer sel = getIntIn("\nEnter Selection (Enter to Quit): ");
			
			sel = (Objects.isNull(sel) ? -1 : sel);
			
			processSelection(sel);
		
		} catch (Exception e) {
			System.out.println("\nInvalid Selection!");
			e.printStackTrace();
			System.out.println();
		}
		
	}
	
	public void processSelection(int sel) {
		
		switch(sel) {
		
		case -1:
			done = true;
			break;
			
		default:
			System.out.println("\nInvalid Input!");
			break;
			
		case 1:
			createProject();
			break;
			
		case 2:
			listProjects();
			break;
			
		case 3:
			selectProject();
			break;
			
		case 4:
			updateProject();
			break;
			
		case 5:
			deleteProject();
			break;
		
		}
		
	}
	
	private void deleteProject() {
		
		listProjects();
		
		Integer id = getIntIn("Select a Project to Delete\nEnter ID: ");
		
		ps.deleteProject(id);
		
	}

	private void updateProject() {
		
		if(Objects.isNull(curProject)) {
			System.out.println("\nNo Project Currently Selected!\nPlease Select One.\n");
		}
		
		System.out.println("Enter a New Value Otherwise Press Enter to Leave Unchanged\n");
		
		String name = getStringIn("Enter Project Name [" + curProject.getProjectName() + "]:");
		BigDecimal estHours = getDecimalIn("Enter Estimated Hours [" + curProject.getEstimatedHours()+ "]:");
		BigDecimal actHours = getDecimalIn("Enter Actual Hours [" + curProject.getActualHours() + "]:");
		Integer diff = getIntIn("Enter the Project's Difficulty (1-5) [" + curProject.getDifficulty() + "]:");
		String notes = getStringIn("Enter Project Notes [" + curProject.getNotes() + "]:");
		
		Project p = new Project();
		
		p.setProjectName(Objects.isNull(name) ? curProject.getProjectName() : name);
		p.setEstimatedHours(Objects.isNull(estHours) ? curProject.getEstimatedHours() : estHours);
		p.setActualHours(Objects.isNull(actHours) ? curProject.getEstimatedHours() : actHours);
		p.setDifficulty(Objects.isNull(diff) ? curProject.getDifficulty() : diff);
		p.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
		
		p.setProjectId(curProject.getProjectId());
		
		ps.modifyProject(p);
		
	}

	private void selectProject() {
		listProjects();
		
		Integer id = getIntIn("Enter Project ID: ");
		curProject = null;
		
		curProject = ps.getProjectById(id);
		
		if(Objects.isNull(curProject)) {
			System.out.println("Invaild ID Selected!");
		} else {
			System.out.println("Selected Project: " + curProject);
		}
		
	}

	private void listProjects() {
		
		List<Project> p = ps.getProjects();
		
		System.out.println("\nProjects:");
		p.forEach(project -> System.out.println(project.getProjectId() + ". " + project.getProjectName()));
		System.out.println();
		
	}

	private void createProject() {
		
		String name = getStringIn("Enter Project Name: ");
		BigDecimal estHours = getDecimalIn("Enter Estimated Hours: ");
		BigDecimal actHours = getDecimalIn("Enter Actual Hours: ");
		Integer diff = getIntIn("Enter the Project's Difficulty (1-5): ");
		String notes = getStringIn("Enter Project Notes: ");
		
		Project p = new Project();
		
		p.setProjectName(name);
		p.setEstimatedHours(estHours);
		p.setActualHours(actHours);
		p.setDifficulty(diff);
		p.setNotes(notes);
		
		Project p2 = ps.addProject(p);
		System.out.println("Successfully Made: " + p2);
		
	}

	private BigDecimal getDecimalIn(String s) {
		String in = getStringIn(s);
		
		if(Objects.isNull(in)) {
			return null;
		}
		
		return new BigDecimal(in).setScale(2);
	}

	public String getStringIn(String s) {
		
		System.out.print(s);
		
		String s1 = sc.nextLine();
		
		return ( s1.isBlank() ? null : s1.trim());
		
	}
	
	public Integer getIntIn(String s) {
		
		String in = getStringIn(s);
		
		if(Objects.isNull(in)) {
			return null;
		}
		
		Integer x = Integer.parseInt(in);
			
		return x;
		
	}

}
