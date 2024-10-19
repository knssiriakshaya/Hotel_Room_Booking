import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

class Hotel {
    String name;
    int totalRooms;
    ArrayList<Integer>[] availableRooms;
    double[] roomPrices;

    @SuppressWarnings("unchecked")
    public Hotel(String name, int totalRooms, double[] roomPrices) {
        this.name = name;
        this.totalRooms = totalRooms;
        this.availableRooms = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            availableRooms[i] = new ArrayList<>();
            for (int j = 1; j <= 5; j++) {
                availableRooms[i].add(j);
            }
        }
        this.roomPrices = roomPrices;
    }

    public boolean bookRoom(int roomType, int roomNumber) {
        if (availableRooms[roomType - 1].contains(roomNumber)) {
            availableRooms[roomType - 1].remove((Integer) roomNumber); // Mark room as booked
            return true;
        }
        return false; // Room not available
    }

    public double getRoomPrice(int roomType) {
        return roomPrices[roomType - 1];
    }

    public String getRoomTypeName(int roomType) {
        if (roomType == 1) {
            return "Single";
        } else if (roomType == 2) {
            return "Double";
        } else if (roomType == 3) {
            return "Suite";
        }
        return "Unknown";
    }

    public ArrayList<Integer> getAvailableRooms(int roomType) {
        return new ArrayList<>(availableRooms[roomType - 1]);
    }
}

public class HotelBookingApp {
    public static void main(String[] args) {
        Hotel[] hotels = {
                new Hotel("ITC Grand Chola", 15, new double[]{4500.0, 9500.0, 10000.0}),
                new Hotel("Hyatt place", 15, new double[]{3500.0, 5600.0, 7000.0}),
                new Hotel("Novotel", 15, new double[]{5000.0, 7000.0, 10000.0}),
                new Hotel("Lemon Tree", 15, new double[]{3200.0, 50000.0, 6500.0}),
                new Hotel("The Gateway", 15, new double[]{5000.0, 7500.0, 10000.0})
        };

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Hotel Booking System!");

        // Display available hotels
        System.out.println("Available Hotels:");
        for (int i = 0; i < hotels.length; i++) {
            System.out.println((i + 1) + ". " + hotels[i].name);
        }

        // Select hotel
        System.out.print("Enter the hotel number: ");
        int hotelChoice = scanner.nextInt();
        Hotel selectedHotel = hotels[hotelChoice - 1];

        // Display available rooms with their costs and room numbers
        System.out.println("Available Rooms in " + selectedHotel.name + ":");
        for (int i = 0; i < 3; i++) {
            ArrayList<Integer> availableRooms = selectedHotel.getAvailableRooms(i + 1);
            double roomCost = selectedHotel.getRoomPrice(i + 1);

            System.out.println(selectedHotel.getRoomTypeName(i + 1) + " - Rs." + roomCost + " per day:");

            if (!availableRooms.isEmpty()) {
                for (int roomNumber : availableRooms) {
                    System.out.print(roomNumber + " ");
                }
                System.out.println();
            } else {
                System.out.println("No available rooms");
            }
        }

        // Enter booking details
        System.out.print("\nEnter the type of room (Single, Double, or Suite): ");
        String roomTypeInput = scanner.next();
        int roomType = getRoomType(roomTypeInput);

        if (roomType == -1) {
            System.out.println("Invalid room type. Exiting program.");
            return;
        }

        // Display available rooms for the selected type
        ArrayList<Integer> availableRoomsForType = selectedHotel.getAvailableRooms(roomType);
        System.out.println("Available rooms for " + selectedHotel.getRoomTypeName(roomType) + ": " + availableRoomsForType);

        // Enter the room number
        System.out.print("Enter the room number you want to book: ");
        int roomNumber = scanner.nextInt();
        if (!selectedHotel.bookRoom(roomType, roomNumber)) {
            System.out.println("Room not available. Please choose another room.");
            return;
        }

        // Enter the number of days
        System.out.print("Enter the number of days: ");
        int numberOfDays = scanner.nextInt();

        double roomPrice = selectedHotel.getRoomPrice(roomType);

        // Get current date for booking date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String bookingDate = dateFormat.format(new Date());

        // Display booking details
        System.out.println("\n=========================================================================");
        System.out.println("\nBooking Details:");
        System.out.println("Hotel: " + selectedHotel.name);
        System.out.println("Room Type: " + selectedHotel.getRoomTypeName(roomType));
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Number of Days: " + numberOfDays);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("\n=========================================================================");
        System.out.println("Total Price: Rs." + (roomPrice * numberOfDays));
        System.out.println("\n=========================================================================");

        scanner.close();
    }

    private static int getRoomType(String roomTypeInput) {
        if (roomTypeInput.equalsIgnoreCase("Single")) {
            return 1;
        } else if (roomTypeInput.equalsIgnoreCase("Double")) {
            return 2;
        } else if (roomTypeInput.equalsIgnoreCase("Suite")) {
            return 3;
        } else {
            return -1; // Invalid room type
        }
    }
}