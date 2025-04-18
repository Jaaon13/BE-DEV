import java.util.Scanner;

public class App {
	
	static boolean wincon = true;
	
	static int[] p = {10,10,10,10,10,10,10,10,10};
	
	static int counter = 0;
	
	static Scanner s = new Scanner(System.in);
	
	public static void draw() {
		
		if(counter > 1) {
			counter = 0;
		}
		
		String hb = "+---+---+---+";
		
		System.out.println(hb);
		
		for(int x = 0; x < 9; x++) {
			System.out.print("| ");
			
			if(p[x] == 0) {
				System.out.print("X ");
			} else if(p[x] == 1) {
				System.out.print("O ");
			} else {
				System.out.print("- ");
			}
			
			if((x + 1) % 3 == 0) {
				System.out.print("| " + Integer.toString(x-1) + " - " + Integer.toString(x+1) + "\n" + hb + "\n");
			}
		}
		
		String t = "";
		
		if (counter == 0) {
			t += "X";
		} else if (counter == 1) {
			t += "O";
		} else {
			System.out.println("Error");
			return;
		}
		if(wincon) {
			System.out.println(t + "'s turn, make a selection:");
		}
		
	}
	
	public static void sel(int s) {
		
		if(counter == 0 && s > 0 && s < 10 && p[s-1] == 10) {
			p[s-1] = 0;
		} else if(counter == 1 && s > 0 && s < 10 && p[s-1] == 10) {
			p[s-1] = 1;
		} else {
			System.out.println("Invalid selection, try again");
			counter--;
			return;
		}
		
	}
	
	public static void tie() {
		int d = 0;
		
		for(int x = 0; x < 9; x++) {
			if(p[x] == 10) {
				d++;
			}
		}
		
		if(d > 0) {
			return;
		} else {
			wincon = false;
			System.out.println("Tie!");
			draw();
		}
	}
	
	public static void horz() {
		int d = 0;
		
		if(p[0] == p[1] && p[1] == p[2] && (p[0] + p[1] + p[2]) < 4) {
			d++;
		} else if(p[3] == p[4] && p[4] == p[5] && (p[3] + p[4] + p[5]) < 4) {
			d++;
		} else if(p[6] == p[7] && p[7] == p[8] && (p[6] + p[7] + p[8]) < 4) {
			d++;
		}
		
		if(d > 0) {
			wincon = false;
			String t = "";
			
			if(counter - 1 == 0) {
				t += "X";
			} else if(counter - 1 == 1) {
				t += "O";
			}
			
			System.out.println(t + " Wins!");
			draw();
		}
		
	}
	
	public static void vert() {
		int d = 0;
		
		if(p[0] == p[3] && p[3] == p[6] && (p[0] + p[3] + p[6]) < 4) {
			d++;
		} else if(p[1] == p[4] && p[4] == p[7] && (p[1] + p[4] + p[7]) < 4) {
			d++;
		} else if(p[2] == p[5] && p[5] == p[8] && (p[2] + p[5] + p[8]) < 4) {
			d++;
		}
		if(d > 0) {
			wincon = false;
			String t = "";
			
			if(counter - 1 == 0) {
				t += "X";
			} else if(counter - 1 == 1) {
				t += "O";
			}
			
			System.out.println(t + " Wins!");
			draw();
		}
	}
	
	public static void diag() {
		int d = 0;
		
		if(p[0] == p[4] && p[4] == p[8] && (p[0] + p[4] + p[8]) < 4) {
			d++;
		} else if(p[2] == p[4] && p[4] == p[6] && (p[2] + p[4] + p[6]) < 4) {
			d++;
		}
		
		if(d > 0) {
			wincon = false;
			String t = "";
			
			if(counter - 1 == 0) {
				t += "X";
			} else if(counter - 1 == 1) {
				t += "O";
			}
			
			System.out.println(t + " Wins!");
			draw();
		}
	}
	
	public static void win() {
		tie();
		
		horz();
		
		vert();
		
		diag();
		
	}

	public static void main(String[] args) {
		while(wincon) {
			draw();
			
			int t = s.nextInt();
			
			sel(t);
			
			counter++;
			
			win();
		}
		
	}

}
