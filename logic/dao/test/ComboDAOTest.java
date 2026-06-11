// src/logic/dao/test/ComboDAOTest.java
package logic.dao.test;

import java.util.List;
import java.util.ArrayList;
import logic.dao.ComboDAO;
import logic.dao.ComponentDAO;
import logic.dao.impl.ComboDAOImpl;
import logic.dao.impl.ComponentDAOImpl;
import logic.model.Combo;
import logic.model.Component;
import logic.util.DAOException;

public class ComboDAOTest {
    public static void main(String[] args) {
        ComboDAO comboDao       = new ComboDAOImpl();
        ComponentDAO compDao    = new ComponentDAOImpl();

        try {
            // 1) Antes
            List<Combo> antes = comboDao.findAll();
            System.out.println("Antes de insertar Combos: " + antes.size());

            // 2) Prepara componentes existentes
            List<Component> comps = compDao.findAll();
            if (comps.isEmpty()) {
                System.out.println("No hay componentes cargados. Inserta al menos uno para probar Combo.");
                return;
            }
            ArrayList<Component> lista = new ArrayList<>();
            lista.add(comps.get(0));
            lista.add(comps.get(1));
            // 3) Insertar Combo
            Combo combo = new Combo(0, "Combo Test", 15, lista);
            comboDao.insert(combo);
            System.out.println("Insertado Combo con id=" + combo.getId());

            // 4) findById
            Combo traido = comboDao.findById(combo.getId());
            System.out.println("Traído: desc=" + traido.getName() + ", descuento=" + traido.getDiscount() + "%");

            // 5) Update
            traido.setDiscount(25);
            comboDao.update(traido);
            System.out.println("Actualizado descuento a " + comboDao.findById(combo.getId()).getDiscount() + "%");

            // 6) Delete
            //comboDao.delete(combo.getId());
            //System.out.println("Borrado. findById devuelve " + comboDao.findById(combo.getId()));

            // 7) Después
            System.out.println("Después de borrar Combos: " + comboDao.findAll().size());

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
