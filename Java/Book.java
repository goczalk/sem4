class Book {
	private int ID;
	private int ISBN;
	private String title;
	private String author;
		
	//dopisz this
		
	public Book(int id, int isbn, String tit, String auth){
		ID = id;
		ISBN = isbn;
		title = tit;
		author = auth;
	};
	
	public Book(String csvString){
		String[] arrayLine = csvString.split(",");
		this.ID = Integer.parseInt(arrayLine[0]);
		this.ISBN = Integer.parseInt(arrayLine[1]);
		this.title  = arrayLine[2];
		this.author = arrayLine[3];
	};
	
	public String getCsvString(){
		String str = this.getID() + "," + this.getISBN() + "," + this.getTitle() + "," + this.getAuthor() + "\n";
		return str;
	};
	
	public int getID(){
		return ID;
	};
	
	public void setISBN(int isbn){
		ISBN = isbn;
	};
	
	public int getISBN(){
		return ISBN;
	};
	
	public void setTitle(String tit){
		title = tit;
	};
	
	public String getTitle(){
		return title;
	};
	
	public void setAuthor(String auth){
		author = auth;
	};
	
	public String getAuthor(){
		return author;
	};
	
	public String getFriendlyString(){
		String str = "ID: " + this.getID() + ", title: " + this.getTitle() + ", author: " + this.getAuthor() + ", ISBN: " + this.getISBN();
		return str;
	};
}
