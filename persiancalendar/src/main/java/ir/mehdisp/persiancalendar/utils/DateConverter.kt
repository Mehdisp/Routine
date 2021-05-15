package ir.mehdisp.persiancalendar.utils

import ir.mehdisp.persiancalendar.models.CivilDate
import ir.mehdisp.persiancalendar.models.IslamicDate
import ir.mehdisp.persiancalendar.models.PersianDate
import kotlin.math.floor

object DateConverter {

    fun civilToIslamic(civil: CivilDate, offset: Int): IslamicDate {
        return jdnToIslamic(civilToJdn(civil) + offset)
    }

    fun civilToJdn(civil: CivilDate): Long {
        val lYear = civil.year.toLong()
        val lMonth = civil.month.toLong()
        val lDay = civil.dayOfMonth.toLong()
        return if (lYear > 1582 || lYear == 1582L && lMonth > 10 || lYear == 1582L && lMonth == 10L && lDay > 14) {
            1461 * (lYear + 4800 + (lMonth - 14) / 12) / 4 + 367 * (lMonth - 2 - 12 * ((lMonth - 14) / 12)) / 12 - 3 * ((lYear + 4900 + (lMonth - 14) / 12) / 100) / 4 + lDay - 32075
        } else julianToJdn(lYear, lMonth, lDay)
    }

    fun civilToPersian(civil: CivilDate): PersianDate {
        return jdnToPersian(civilToJdn(civil))
    }

    private fun floor(d: Double): Long {
        return kotlin.math.floor(d).toLong()
    }

    fun islamicToCivil(islamic: IslamicDate): CivilDate {
        return jdnToCivil(islamicToJdn(islamic))
    }

    fun islamicToJdn(islamic: IslamicDate): Long {
        // NMONTH is the number of months between julian day number 1 and
        // the year 1405 A.H. which started immediatly after lunar
        // conjunction number 1048 which occured on September 1984 25d
        // 3h 10m UT.
        val NMONTHS = 1405 * 12 + 1
        var year = islamic.year
        val month = islamic.month
        val day = islamic.dayOfMonth
        if (year < 0) year++
        val k = (month + year * 12 - NMONTHS).toLong() // nunber of months since 1/1/1405
        return floor(visibility(k + 1048) + day + 0.5)
    }

    fun islamicToPersian(islamic: IslamicDate): PersianDate {
        return jdnToPersian(islamicToJdn(islamic))
    }

    fun jdnToCivil(jdn: Long): CivilDate {
        return if (jdn > 2299160) {
            var l = jdn + 68569
            val n = 4 * l / 146097
            l -= (146097 * n + 3) / 4
            val i = 4000 * (l + 1) / 1461001
            l = l - 1461 * i / 4 + 31
            val j = 80 * l / 2447
            val day = (l - 2447 * j / 80).toInt()
            l = j / 11
            val month = (j + 2 - 12 * l).toInt()
            val year = (100 * (n - 49) + i + l).toInt()
            CivilDate(year, month, day)
        } else jdnToJulian(jdn)
    }

    fun jdnToIslamic(jd: Long): IslamicDate {
        val civil = jdnToCivil(jd)
        var year = civil.year
        var month = civil.month
        var day = civil.dayOfMonth
        var k =
            floor(0.6 + (year + (if (month % 2 == 0) month else month - 1) / 12.0 + day / 365f - 1900) * 12.3685)
        var mjd: Double
        do {
            mjd = visibility(k)
            k -= 1
        } while (mjd > jd - 0.5)
        k += 1
        val hm = k - 1048
        year = 1405 + (hm / 12).toInt()
        month = (hm % 12).toInt() + 1
        if (hm != 0L && month <= 0) {
            month += 12
            year -= 1
        }

        if (year <= 0)
            year -= 1

        day = floor(jd - mjd + 0.5).toInt()
        return IslamicDate(year, month, day)
    }

    // TODO Is it correct to return a CivilDate as a JulianDate?
    fun jdnToJulian(jdn: Long): CivilDate {
        var j = jdn + 1402
        val k = (j - 1) / 1461
        val l = j - 1461 * k
        val n = (l - 1) / 365 - l / 1461
        var i = l - 365 * n + 30
        j = 80 * i / 2447
        val day = (i - 2447 * j / 80).toInt()
        i = j / 11
        val month = (j + 2 - 12 * i).toInt()
        val year = (4 * k + n + i - 4716).toInt()
        return CivilDate(year, month, day)
    }

    fun jdnToPersian(jdn: Long): PersianDate {
        val depoch = jdn - persianToJdn(475, 1, 1)
        val cycle = depoch / 1029983
        val cyear = depoch % 1029983
        val ycycle: Long
        val aux1: Long
        val aux2: Long
        if (cyear == 1029982L) ycycle = 2820 else {
            aux1 = cyear / 366
            aux2 = cyear % 366
            ycycle = floor((2134 * aux1 + 2816 * aux2 + 2815) / 1028522.0) + aux1 + 1
        }
        var year: Int
        val month: Int
        val day: Int
        year = (ycycle + 2820 * cycle + 474).toInt()
        if (year <= 0) year = year - 1
        val yday = jdn - persianToJdn(year, 1, 1) + 1
        month = if (yday <= 186) Math.ceil(yday / 31.0)
            .toInt() else Math.ceil((yday - 6) / 30.0)
            .toInt()
        day = (jdn - persianToJdn(year, month, 1)).toInt() + 1
        return PersianDate(year, month, day)
    }

    fun julianToJdn(lYear: Long, lMonth: Long, lDay: Long): Long {
        return 367 * lYear - 7 * (lYear + 5001 + (lMonth - 9) / 7) / 4 + 275 * lMonth / 9 + lDay + 1729777
    }

    fun persianToCivil(persian: PersianDate): CivilDate {
        return jdnToCivil(persianToJdn(persian))
    }

    fun persianToIslamic(persian: PersianDate): IslamicDate {
        return jdnToIslamic(persianToJdn(persian))
    }

    fun persianToJdn(year: Int, month: Int, day: Int): Long {
        val PERSIAN_EPOCH: Long = 1948321 // The JDN of 1 Farvardin 1
        val epbase: Long
        epbase = if (year >= 0) (year - 474).toLong() else (year - 473).toLong()
        val epyear = 474 + epbase % 2820
        val mdays: Long
        mdays = if (month <= 7) ((month - 1) * 31).toLong() else ((month - 1) * 30 + 6).toLong()
        return day + mdays + (epyear * 682 - 110) / 2816 + (epyear - 1) * 365 + epbase / 2820 * 1029983 + (PERSIAN_EPOCH - 1)
    }

    fun persianToJdn(persian: PersianDate): Long {
        val year = persian.year
        val month = persian.month
        val day = persian.dayOfMonth
        val PERSIAN_EPOCH: Long = 1948321 // The JDN of 1 Farvardin 1
        val epbase = if (year >= 0) (year - 474).toLong() else (year - 473).toLong()
        val epyear = 474 + epbase % 2820
        val mdays = if (month <= 7) ((month - 1) * 31).toLong() else ((month - 1) * 30 + 6).toLong()
        return day + mdays + (epyear * 682 - 110) / 2816 + (epyear - 1) * 365 + epbase / 2820 * 1029983 + (PERSIAN_EPOCH - 1)
    }

    private fun tmoonphase(n: Long, nph: Int): Double {
        val RPD = 1.74532925199433E-02 // radians per degree
        // (pi/180)
        var xtra: Double
        val k = n + nph / 4.0
        val T = k / 1236.85
        val t2 = T * T
        val t3 = t2 * T
        val jd =
            2415020.75933 + 29.53058868 * k - 0.0001178 * t2 - 0.000000155 * t3 + 0.00033 * Math.sin(
                RPD * (166.56 + 132.87 * T - 0.009173 * t2)
            )

        // Sun's mean anomaly
        val sa = RPD * (359.2242 + 29.10535608 * k - 0.0000333 * t2 - 0.00000347 * t3)

        // Moon's mean anomaly
        val ma = RPD * (306.0253 + 385.81691806 * k + 0.0107306 * t2 + 0.00001236 * t3)

        // Moon's argument of latitude
        val tf = RPD * 2.0 * (21.2964 + 390.67050646 * k - 0.0016528 * t2 - 0.00000239 * t3)
        when (nph) {
            0, 2 -> xtra =
                (0.1734 - 0.000393 * T) * Math.sin(sa) + 0.0021 * Math.sin(sa * 2) - 0.4068 * Math.sin(
                    ma
                ) + 0.0161 * Math.sin(2 * ma) - 0.0004 * Math.sin(3 * ma) + 0.0104 * Math.sin(tf) - 0.0051 * Math.sin(
                    sa + ma
                ) - 0.0074 * Math.sin(sa - ma) + 0.0004 * Math.sin(tf + sa) - 0.0004 * Math.sin(tf - sa) - 0.0006 * Math.sin(
                    tf + ma
                ) + 0.001 * Math.sin(tf - ma) + 0.0005 * Math.sin(sa + 2 * ma)
            1, 3 -> {
                xtra =
                    (0.1721 - 0.0004 * T) * Math.sin(sa) + 0.0021 * Math.sin(sa * 2) - 0.628 * Math.sin(
                        ma
                    ) + 0.0089 * Math.sin(2 * ma) - 0.0004 * Math.sin(3 * ma) + 0.0079 * Math.sin(tf) - 0.0119 * Math.sin(
                        sa + ma
                    ) - 0.0047 * Math.sin(sa - ma) + 0.0003 * Math.sin(tf + sa) - 0.0004 * Math.sin(
                        tf - sa
                    ) - 0.0006 * Math.sin(tf + ma) + 0.0021 * Math.sin(tf - ma) + 0.0003 * Math.sin(
                        sa + 2 * ma
                    ) + 0.0004 * Math.sin(sa - 2 * ma) - 0.0003 * Math.sin(2 * sa + ma)
                xtra =
                    if (nph == 1) xtra + 0.0028 - 0.0004 * Math.cos(sa) + 0.0003 * Math.cos(
                        ma
                    ) else xtra - 0.0028 + 0.0004 * Math.cos(sa) - 0.0003 * Math.cos(ma)
            }
            else -> return 0.0
        }
        // convert from Ephemeris Time (ET) to (approximate)Universal Time (UT)
        return jd + xtra - (0.41 + 1.2053 * T + 0.4992 * t2) / 1440
    }

    private fun visibility(n: Long): Double {

        // parameters for Makkah: for a new moon to be visible after sunset on
        // a the same day in which it started, it has to have started before
        // (SUNSET-MINAGE)-TIMZ=3 A.M. local time.
        val TIMZ = 3f
        val MINAGE = 13.5f
        val SUNSET = 19.5f
        // approximate
        val TIMDIF = SUNSET - MINAGE
        val jd = tmoonphase(n, 0)
        val d = floor(jd)
        var tf = jd - d
        return if (tf <= 0.5) // new moon starts in the afternoon
            jd + 1f else { // new moon starts before noon
            tf = (tf - 0.5) * 24 + TIMZ // local time
            if (tf > TIMDIF) jd + 1.0 // age at sunset < min for visiblity
            else jd
        }
    }
}