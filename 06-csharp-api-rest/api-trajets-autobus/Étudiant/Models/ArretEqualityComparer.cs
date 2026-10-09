using System.Diagnostics.CodeAnalysis;

namespace TP3.Models
{
    public class ArretEqualityComparer : IEqualityComparer<Arret>
    {

        public bool Equals(Arret? x, Arret? y)
        {
            if (ReferenceEquals(x, y))
            {
                return true;
            }

            if (x == null && y == null)
            {
                return true;
            }

            if (x == null)
            {
                return false;
            }

            if (y == null)
            {
                return false;
            }

            return x.Latitude.Equals(y.Latitude) && x.Longitude.Equals(y.Longitude);
        }

        public int GetHashCode([DisallowNull] Arret obj)
        {
            return obj.Id.GetHashCode();
        }
    }
}
