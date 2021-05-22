import com.model.*;
import com.view.*;
import com.controller.*;

/*
    Initialize register all components of MVC model.
 */

public class Main {

    public static void main(String[] args)   {
        Model m = new Model();
        Controller c = new Controller();
        View v = new View();

        m.registerView(v);        // View goes in the Model
        c.setModel(m);            // Model goes in controller
        v.setActionListener(c);   // Controller goes in View

    }
}
