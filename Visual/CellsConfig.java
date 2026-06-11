package Visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellsConfig extends DefaultTableCellRenderer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type = "text";
	
	//DEFINICION DE LOS TIPOS DE LETRAS A UTILIZAR
	private Font normal = new Font("Centaur",Font.PLAIN,12);
	private Font bold = new Font("Centaur", Font.BOLD, 12);
	
	
	
	//Se obtienen los iconos diponibles para ser mostrados segun se necesite
	//private ImageIcon icon = new ImageIcon(getClass().getResource("direction of the icon that's going to be used "));

	public CellsConfig(String type)
	{
		this.type = type;
	}
	
	public Component getTavleCellRendererComponent(JTable ClientTable, Object value, boolean selected, boolean focused, int row, int column)
	{
		//Este metodo se encargara de controlar toda la tabla, cada evento realizado sobre la tabla invocara a este metodo
		
		//Se definen los colores default
		
		Color background = null;
		Color backgroundDefault = new Color(192, 192, 192);
		Color backgroundSelection = new Color(140, 140, 140);
		
		//Si la celda del evento es seleccionada se asigna el fondo por defecto para la seleccion
		if(selected)
		{
			this.setBackground(backgroundDefault);
		}
		else
		{
			//Para las que no esten seleccionadas estaran de color blanco
			this.setBackground(Color.white);
		}
		
		/*
		 *  Se definen los tipos de datos que estaran contenidos en las celdas 
		 *  basado en la instancia que hace la ventana al momento de construirla
		 */
		
		if(type.equals("text"))
		{
			//si el tipo texto, se define el color de fondo del texto y de la celda asi como la alineacion
			if(focused)
			{
				background = backgroundSelection;
			}
			else
			{
				background = backgroundDefault;
			}
			
			this.setHorizontalAlignment(JLabel.LEFT);
			this.setText((String)value);
			this.setBackground((selected)? background : Color.white);
			this.setFont(normal);
			
			return this;
		}
		
		//personalizacion para el dato numerico
		if(type.equals("number"))
		{
			if(focused)
			{
				background = backgroundSelection;
			}
			else
			{
				background = backgroundDefault;
			}
			
			this.setHorizontalAlignment(JLabel.RIGHT);
			this.setText((String)value);
			this.setForeground((selected)? new Color (255, 255, 255) : new Color (32, 117, 32));
			this.setBackground((selected)? background : Color.white);
			this.setFont(bold);
			
			return this;
		}
		
		return this;
		
	}
	
}

	