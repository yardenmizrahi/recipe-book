package model;

public class Picture {
	private int pictureId;
	//private static int countPictureId = 1;
	
	private String title;
	private String author;
	private String description;
	private Recipe recipe;
	
	public Picture(int pictureId, String title, String author, String description, Recipe r) {
		this.pictureId = pictureId;
		this.title = title;
		this.author = author;
		this.description = description;
		this.recipe = r;
	}
	
	public Picture(int pictureId, String title, String author, String description) {
		this.pictureId = pictureId;
		this.title = title;
		this.author = author;
		this.description = description;
	}

	public int getPictureId() {
		return pictureId;
	}

	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe r) {
		this.recipe = r;
	}

	@Override
	public String toString() {
		return "Picture [pictureId=" + pictureId + ", title=" + title + ", author=" + author + ", description="
				+ description + "]";
	}
	
}
