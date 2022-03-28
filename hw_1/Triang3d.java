import geom.*;
import java.util.ArrayList;
import static geom.Vect3d.*;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

/**
 * Triangle in 3d.
 */
public class Triang3d extends Polygon3d {

  // ---------- Задание 1 -----------
  
  /**
   * Возвращает медиану треугольника по переданной
   * стороне и вершине.
   * 
   * В полученной медиане точки:
   *  a - переданная вершина
   *  b - центр переданной медианы
   * 
   * @param vertex вершина содержащая медиану
   * @param rib сторона, содержащая медиану
   * @return найденная медиана
   * 
   * Вспомогательный метод.
   */
  private Rib3d median2(Vect3d vertex, Rib3d rib) throws ExDegeneration {
    return new Rib3d(vertex, rib.center());
  }
  
  /**
   * Возвращает медиану треугольника принадлежащую отрезку AB
   */
  public Rib3d medianAB() throws ExDegeneration {
    return median2(c(), ab());
  }
  
  /**
   * Возвращает медиану треугольника принадлежащую отрезку BC
   */
  public Rib3d medianBC() throws ExDegeneration {
    return median2(a(), bc());
  }
  
  /**
   * Возвращает медиану треугольника принадлежащую отрезку CA
   */
  public Rib3d medianCA() throws ExDegeneration {
    return median2(b(), ca());
  }
  
  // ---------- Задание 2 -----------
  
  /**
   * Возвращает симедиану треугольника по переданным вершинам.
   * Для расчёта симедианы берётся вершина a и отрезок bc
   * 
   * В полученной медиане точки:
   *  a - переданная вершина
   *  b - центр переданной медианы
   * 
   * @param vertex вершина содержащая симедиану
   * @param rib сторона, содержащая симедиану
   * @return найденная симедиана
   * 
   * Вспомогательный метод.
   */
  private Rib3d cmedian(Vect3d vertex, Rib3d bc) throws ExDegeneration {
    // получим медиану и биссектрису
    Rib3d med = median2(vertex, bc);
    Rib3d bisect = bisectrix(vertex, bc.a(), bc.b());
      
    // построим прямую симметричную прямой med относительно bissect
    Line3d cmedLine = 
      (Line3d) SpaceTransformation.objectSymUnderLine(med.line(), 
        bisect.line());
    
    // построим Rib3d симедианы по вершине и точке пересечения с bc
    return new Rib3d(vertex, cmedLine.intersectionWithLine(bc.line()));   
  }
  
  /**
   * Возвращает симедиану треугольника принадлежащую отрезку AB
   */
  public Rib3d cmedianAB() throws ExDegeneration {
    return cmedian(c(), ab());
  }
  
  /**
   * Возвращает симедиану треугольника принадлежащую отрезку BC
   */
  public Rib3d cmedianBC() throws ExDegeneration {
    return cmedian(a(), bc());
  }
  
  /**
   * Возвращает симедиану треугольника принадлежащую отрезку CA
   */
  public Rib3d cmedianCA() throws ExDegeneration {
    return cmedian(b(), ca());
  }

  // ---------- Задание 3 -----------  
  /**
   * @return точка пересечения симедиан
   */
  public Vect3d lemuansPoint() throws ExDegeneration {
    return cmedianAB().intersectWithLine(cmedianBC().line());
  }
}