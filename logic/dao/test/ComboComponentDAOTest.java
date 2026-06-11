package logic.dao.test;

import java.util.ArrayList;
import java.util.List;

import logic.dao.ComboComponentDAO;
import logic.dao.ComboDAO;
import logic.dao.ComponentDAO;
import logic.dao.impl.ComboComponentDAOImpl;
import logic.dao.impl.ComboDAOImpl;
import logic.dao.impl.ComponentDAOImpl;
import logic.model.Administration;
import logic.model.Combo;
import logic.model.ComboComponent;
import logic.model.Component;
import logic.util.DAOException;

public class ComboComponentDAOTest {
    public static void main(String[] args) {
        ComboDAO comboDao = new ComboDAOImpl();
        ComponentDAO compDao = new ComponentDAOImpl();
        ComboComponentDAO ccDao = new ComboComponentDAOImpl();

        try {
            // 1) Crear componente de prueba
            Component compTest = new Component(Administration.ComponentId, "MarcaTest", 500.00 , 10, 4, 0);

            // Ya no borrar antes para evitar perder datos

            compDao.insert(compTest);
            System.out.println("Insertado componente de prueba con id=" + compTest.getId());

            // 2) Crear combo de prueba
            List<Component> listaComps = new ArrayList<>();
            listaComps.add(compTest);
            Combo comboTest = new Combo(0, "ComboTestDAO", 10, listaComps);

            // Ya no borrar combo antes

            comboDao.insert(comboTest);
            System.out.println("Insertado combo de prueba con id=" + comboTest.getId());

            // 3) Insertar relación ComboComponent solo si no existe
            ComboComponent cc = new ComboComponent(comboTest.getId(), compTest.getId(), 3);
            ComboComponent existente = ccDao.find(comboTest.getId(), compTest.getId());
            if (existente == null) {
                ccDao.insert(cc);
                System.out.println("Insertado en COMBO_COMPONENT: combo=" + cc.getIdCombo() + ", comp=" + cc.getIdComponent());
            } else {
                System.out.println("Ya existía la relación combo-componente. No se insertó de nuevo.");
            }

            // 4) Buscar relación
            ComboComponent encontrado = ccDao.find(comboTest.getId(), compTest.getId());
            System.out.println("Encontrado: " + encontrado.getIdCombo() + " - " + encontrado.getIdComponent() +
                               " cantidad=" + encontrado.getQuantity());

            // 5) Actualizar cantidad
            encontrado.setQuantity(7);
            ccDao.update(encontrado);
            System.out.println("Cantidad actualizada a 7.");

            // 6) Verificar actualización
            ComboComponent actualizado = ccDao.find(comboTest.getId(), compTest.getId());
            System.out.println("Cantidad ahora: " + actualizado.getQuantity());

            // 7) Borrar solo la relación ComboComponent
            ccDao.delete(comboTest.getId(), compTest.getId());
            System.out.println("Relación borrada. find() ahora devuelve: " +
                               ccDao.find(comboTest.getId(), compTest.getId()));

            // No borrar combo ni componente.
            // No se pueden borrar ni combo ni component porque son kf en muchas tablas entonces causa mucho conflicto
            

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
}
