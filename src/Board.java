
public class Board 
{
	
	private int[][] board;
	private int depth;
	private static final int[] standard = {200, 0, 4, 16, 48, 128, 320, 768, 1792, 4096, 9216, 20480};
	private static final int MAX_DEPTH = 4;
	private static int value[];
	
	public void setValue()
	{
		value = new int[12];
		boolean[] has = new boolean[12];
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				has[board[i][j]] = true;
			}
		}
		
		for (int i = 0; i < 12; i++)
		{
			value[i] = standard[i];
			if (!has[i])
			{
				value[i] *= 2;
				//System.out.println(i);
			}
		}
	}
	
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
		for (int i = 0; i < 12; i++)
			System.out.print(value[i] + " ");
		System.out.println();
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
		
		if (count > 8)
		{
			return score();
		}
		else if (count == 0)
		{
			System.out.println("dead");
			print();
			System.exit(1);
		}
		
		double total = 0;
//		double min = Double.MAX_VALUE;
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (board[i][j] == 0)
				{
					Board c = copy();
					c.board[i][j] = 2;
					total += c.score();
//					double score = c.score();
//					min = min > score ? score : min;
				}
			}
		}
		
		return total / count;
//		return min;
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
		
		
	
		Board[] moves = {left(), right(), up(), down()};		
		double max = -4 * sum();
		
		
		//moves[3].print();
		//return -1;
		
		for (int i = 0; i < 4; i++)
		{	
			if (moves[i] == null)
				continue;
			double score;
			
			if (depth == MAX_DEPTH) 
			{
				score = moves[i].sum();
			}
			else
			{
				score = moves[i].add2Score();
			}
			
			if (score > max)
			{
				max = score;
			}
		}
		
		return max;
	}
	
	private double sum() 
	{
		int s = 0;
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				s += value[board[i][j]];	
				//System.out.print(s + " ");
			}
		}
		//System.out.println();
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
//			System.out.println(i + " : " + score);
//			moves[i].print();
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
		
		Board r = new Board(depth + 1);
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
					r.board[i - offset - 1][j] += 1;
					if (depth == 0 && r.board[i - offset - 1][j] == 11)
					{
						System.exit(0);
					}
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
