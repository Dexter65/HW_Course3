import geom.ExDegeneration;
import geom.Vect3d;
import java.util.ArrayList;

/**
 * Набор точек в двумерном пространстве
 */
public class Points2d {
  private ArrayList<Vect3d> _points;

  /**
   * Набор точек по переданному листу точек
   * @param points набор точек Vect2d
   */  
  public Points2d(ArrayList<Vect3d> points) throws ExDegeneration {
    // проверка точек на дубли 
    for (int i = 0; i < points.size(); ++i) {
      for (int j = i + 1; j < points.size(); ++j) {
        if (points.get(i).equals(points.get(j))) {
          throw new ExDegeneration("В переданном наборе присутствуют "
                  + "одинаковые точки");
        }
      }
    }
    
    _points = points;
  }
  
  /**
   * возвращяет точку из всего набора по индексу
   * @param index номер точки в наборе (начиная с 0)
   * @return точка под переданным номером
   */
  public Vect3d point(int index) throws ExDegeneration {
    // проверка существование элемента с таким индексом
    if (index < 0 || index >= _points.size()) {
      throw new ExDegeneration("Точка под номером " 
        + index + " отсутствует в наборе");
    }
    
    return _points.get(index);
  }
}
