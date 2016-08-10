package com.example.demo.data;

import com.example.demo.R;

public class ModelItemData {

    public static int getImageDrawable(int position) {
        switch (position) {

            default:
            case 0:
                return R.drawable.pierre_marcolini;
            case 1:
                return R.drawable.mast_brothers;
            case 2:
                return R.drawable.melt;
            case 3:
                return R.drawable.paul_young;
            case 4:
                return R.drawable.la_maison_du_chocolate;
            case 5:
                return R.drawable.artisan_du_chocolate;
            case 6:
                return R.drawable.melange;
            case 7:
                return R.drawable.philip_neal;
        }
    }

    public static ModelItem[] items = {
            new ModelItem("Pierre Marcolini", "37 Marylebone High St, Marylebone, W1U", "https://eu.marcolini.com", "info@marcolini.com", "020 7486 7196"),
            new ModelItem("Mast Brothers", "19-29 Redchurch St, Shoreditch, E2 7DJ", "http://mastbrothers.co.uk/pages/home", "info@mastbrothers.co.uk", "020 7739 1236"),
            new ModelItem("Melt", "59 Ledbury Road, Notting Hill, W11 2AA", "http://www.meltchocolates.com", "info@meltchocolates.com", "020 7727 5030"),
            new ModelItem("Paul A Young Fine Chocolates", "143 Wardour Street, Soho, W1F 8WA", "http://www.paulayoung.co.uk", "info@paulayoung.co.uk", "020 7437 0011"),
            new ModelItem("La Maison Du Chocolat", "46 Piccadilly, Mayfair, W1J 0DS", "http://www.lamaisonduchocolat.co.uk/uk/en/", "info@lamaisonduchocolat.co.uk", "020 7287 8500"),
            new ModelItem("Artisan du Chocolat", "81 Westbourne Grove, Noting Hill, W2 4UL", "http://www.artisanduchocolat.com", "info@artisanduchocolat.com", "0845 270 6996"),
            new ModelItem("Melange", "184 Bellenden Road, Peckham, SE15 4BW", "http://www.themelange.com", "info@themelange.co.uk", "07722 650711"),
            new ModelItem("Philip Neal", "43 Turnham Green Terrace, Chiswick, W4 1RG", "http://www.philipnealchocolates.co.uk", "info@philipnealchocolates.co.uk", "020 8987 3183")
    };



}
