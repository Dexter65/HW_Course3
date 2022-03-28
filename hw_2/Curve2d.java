import geom.ExDegeneration;
import geom.Vect3d;
import java.util.ArrayList;

/**
 * Кривая в двумерном пространстве
 */
public class Curve2d extends Points2d {
  
  /**
   * Конструктор создающий двумерную кривую по набору точек.
   * Кривая создается последовательно от первой до последней точки
   * из набора.
   * Кривая не будет создана, если набор содержит самопересечения
   * @param points набор точек, по которому будет построена кривая
   * @throws ExDegeneration 
   */ 
  public Curve2d(ArrayList<Vect3d> points) throws ExDegeneration {
    super(points);
    
    // Проверка количества точек
    if (points.size() < 2) {
      throw new ExDegeneration("Задано слишком мало точек, для построения"
        + " кривой");
    }
    
    // Проверка на самопересечения (тупым перебором всех отрезков)
    for (int i = 0; i + 1 < points.size(); ++i) {
      for (int j = i + 1; j + 1 < points.size(); ++j) {
        if (IntersectHelper.isInterset(points.get(i), points.get(i + 1),
            points.get(j), points.get(j + 1))) {
          
          throw new ExDegeneration("Кривая по заданным точкам образует"
            + " самопересечение");
        }
      }
    }
    
  } 
}

/**
 * Вспомогательный класс содержащий методы для нахождения пересечений между
 * отрезками
 */
class IntersectHelper {
  
  /**
   * Проверяет пересечение между двумя отрезками.
   * Концы отрезков не включаются в проверку на пересечение.
   * Первый отрезок pt1,pt2
   * Воторой отрезок pt3,pt4
   * @param pt1 первая точка первого отрезка
   * @param pt2 вторая точка первого отрезка
   * @param pt3 первая точка второго отрезка
   * @param pt4 вторая точка второго отрезка
   * @return true, если пересечение двух отрезков существует, иначе false
   */
  public static boolean isInterset(Vect3d pt1, Vect3d pt2, Vect3d pt3, Vect3d pt4) {
    return isOverlappedRibs(pt1, pt2, pt3, pt4) &&
        orientedArea(pt1, pt2, pt3) * orientedArea(pt1, pt2, pt4) <= 0 &&
        orientedArea(pt3, pt4, pt1) * orientedArea(pt3, pt4, pt2) <= 0;
  }
    
    
  /**
   * Метод возвращает орентированную площадь треугольника, построенного
   * по трём точкам.
   * @param pt1 первая точка
   * @param pt2 вторая точка
   * @param pt3 третья точка
   * @return орентированная площадь
   */
  public static double orientedArea(Vect3d pt1, Vect3d pt2, Vect3d pt3) {
    return (pt2.x() - pt1.x()) * (pt3.y() - pt1.y()) -
           (pt2.y() - pt1.y()) * (pt3.x() - pt1.x());
  }
  
  /**
   * Метод проверяет наложение двух отрезков друг на друга
   * по ОДНОЙ из координат (x или y)
   * @param pt1a координата первой точки первого отрезка
   * @param pt1b координата второй точки первого отрезка
   * @param pt2a координата первой точки второго отрезка
   * @param pt2b координата второй точки второго отрезка
   * @return true, если есть пересечение по этой координате и false, если нет
   */
  private static boolean isOverlappedRibsByOneCoord(double pt1a, double pt1b, 
      double pt2a, double pt2b) {
    
    if (pt1a > pt1b) {
      double tmp = pt1a;
      pt1a = pt1b;
      pt1b = tmp;
    }
    if (pt2a > pt2b) {
      double tmp = pt2a;
      pt2a = pt2b;
      pt2b = tmp;
    }
    
    return Math.max(pt1a, pt2a) <= Math.min(pt1b, pt2b);
  }
  
  /**
   * Метод проверяет наложение двух отрезков друг на друга
   * @param pt1 первая точка первого отрезка
   * @param pt1 вторая точка первого отрезка
   * @param pt2 первая точка второго отрезка
   * @param pt2 вторая точка второго отрезка
   * @return true, если есть наложение и false, если его нет
   */
  public static boolean isOverlappedRibs(Vect3d pt1, Vect3d pt2, 
      Vect3d pt3, Vect3d pt4) {
    
    return isOverlappedRibsByOneCoord(pt1.x(), pt2.x(), pt3.x(), pt4.x()) && 
            isOverlappedRibsByOneCoord(pt1.y(), pt2.y(), pt3.y(), pt4.y());
  }
}