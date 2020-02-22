package course;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.function.Function;


// <friendship>
public final class Checked {
    public interface IChecked<T, R> {
        R apply(T t) throws Exception;
    }

    public interface CheckedActionListener<T> {
        void actionPerformed(T e) throws Exception;
    }

    public interface CheckedListSelectionListener<T> {
        void valueChanged(T e) throws Exception;
    }


    public static <T, R> Function<T, R> Checked(IChecked<T, R> e) {
        return (T i) -> {
            try {
                return e.apply(i);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        };
    }

    public static ActionListener CheckedActionListener(CheckedActionListener l) {
        return e -> {
            try {
                l.actionPerformed(e);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        };
    }

    public static ListSelectionListener CheckedListSelectionListener(CheckedListSelectionListener l) {
        return e -> {
            try {
                l.valueChanged(e);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        };
    }
}
// </friendship>
