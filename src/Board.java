
public class Board 
{
	
	private int[][] board;
	private int depth;
	
	public Board(int d)
	{
		board = new int[4][4];
		depth = d;
	}
	
	public void set(int i, int j, int v)
	{
		board[i][j] = v;
	}
	
	public void print()
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				System.out.print(board[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private Board copy()
	{
		Board r = new Board(depth);
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				r.board[i][j] = board[i][j];
			}
		}
		return r;
	}
	
	private double add2Score()
	{
		int count = count();
		
		if (count > 6)
		{
			return score();
		}
		else if (count == 0)
		{
			System.out.println("dead");
			print();
			System.exit(1);
		}
		
		//double total = 0;
		double min = 30000;
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (board[i][j] == 0)
				{
					Board c = copy();
					c.board[i][j] = 2;
//					total += c.score();
					double score = c.score();
					min = min > score ? score : min;
				}
			}
		}
		
//		return total / count;
		return min;
	}
	
	private int count()
	{
		int s = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				if (board[i][j] == 0)
					s++;
			}
		}
		return s;
		
	}
	
	public double score()	
	{
		
		if (depth == 0) return sum();
		
	
		Board[] moves = {left(), right(), up(), down()};		
		double max = -150000;
		
		
		//moves[3].print();
		//return -1;
		
		for (int i = 0; i < 4; i++)
		{	
			if (moves[i] == null)
				continue;
			double score = moves[i].add2Score();
			if (score > max)
			{
				max = score;
			}
		}
		
		return max;
	}
	
	/*private double curve(int s) 
	{
		switch(s)
		{
			case 0: return -1;
			case 1: return 7;
			case 2: return 13;
			case 3: return 18;
			case 4: return 22;
			case 5: return 25;
			case 6: return 27;
			default: return 21 + s;
		}
	}*/

	private double sum() 
	{
		int s = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				switch(board[i][j])
				{
					case 0: 
						break;
					case 2:
						break;
					case 4:
						s += 4;
						break;
					case 8:
						s += 16;
						break;
					case 16:
						s += 48;
						break;
					case 32:
						s += 128;
						break;
					case 64:
						s += 320; 
						break;
					case 128:
						s += 768; 
						break;
					case 256:
						s += 1792; 
						break;
					case 512:
						s += 4096; 
						break;
					case 1024:
						s += 9216; 
						break;
					case 2048:
						s += 20480; 
						break;
					default:
					    System.out.println("....");
					    System.exit(1);
				}
				//s += Math.pow(board[i][j], 1.5);
			}
		}
		return s;
	}

	public int move()
	{
		Board[] moves = {left(), right(), up(), down()};
		
		int move = -1;
		double max = -150000;
		
		//moves[3].print();
		//return -1;
		
		for (int i = 0; i < 4; i++)
		{	
			if (moves[i] == null)
				continue;
			double score = moves[i].score();
			if (score > max)
			{
				max = score;
				move = i;
			}
			//System.out.println(i + " : " + score);
			//moves[i].print();
		}
		
		return move;
		
	}
	
	private Board tran()
	{
		Board r = new Board(depth);
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				r.board[i][j] = board[j][i];
		
		return r;
	}
	
	private Board rev()
	{
		Board r = new Board(depth);
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				r.board[i][j] = board[3 - i][j];
		
		return r;
	}
	
	private Board left()
	{
		boolean movable = false;
		
		Board r = new Board(depth - 1);
		for(int j = 0; j < 4; j++)
		{
			int offset = 0;
			for(int i = 0; i < 4; i++)
			{
				//r.print();
				if (board[i][j] == 0)
				{
					offset++;
				}
				else if (i - offset > 0 && board[i][j] == r.board[i - offset - 1][j] && board[i][j] == board[i - 1][j])
				{
					r.board[i - offset - 1][j] *= 2;
					offset++;
					movable = true;
				}
				else
				{
					r.board[i - offset][j] = board[i][j];
					movable = movable || offset!=0;
				}
			}
		}

		
		if (!movable)
			return null;
		return r;
	}
	
	private Board right()
	{
		Board r = rev().left();
		if (r == null)
			return null;
		return r.rev();
	}
	
	private Board up()
	{
		Board r = tran().left();
		if (r == null)
			return null;
		return r.tran();
	}
	
	private Board down()
	{
		Board r = tran().rev().left();
		if (r == null)
			return null;
		return r.rev().tran();
	}
}
