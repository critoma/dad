package eu.ase.ejb3;

import java.math.*;

import javax.ejb.Stateless;

@Stateless
public class ConverterBean implements ConverterRemote, ConverterLocal
{
   BigDecimal yenRate = new BigDecimal("121.6000");
   BigDecimal euroRate = new BigDecimal("0.0077");

   public BigDecimal dollarToYen(BigDecimal dollars) {
        BigDecimal result = dollars.multiply(yenRate);

        return result.setScale(2, BigDecimal.ROUND_UP);
    }

    public BigDecimal yenToEuro(BigDecimal yen) {
        BigDecimal result = yen.multiply(euroRate);

        return result.setScale(2, BigDecimal.ROUND_UP);
    }

} // ConverterBean
