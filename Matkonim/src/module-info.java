module Matkonim {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
