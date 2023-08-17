fizzbuzz = fn
  0, 0, _ -> "FizzBuzz"
  0, _, 0 -> 0
  0, _, _ -> "Fizz"
  _, 0, 0 -> 0
  _, 0, _ -> "Buzz"
  _, _, a -> a
end

# FizzBuzz
IO.puts("0, 0, 0")
IO.puts(fizzbuzz.(0, 0, 0))

IO.puts("0, 0, 1")
IO.puts(fizzbuzz.(0, 0, 1))

# Fizz
IO.puts("0, 1, 1")
IO.puts(fizzbuzz.(0, 1, 1))

# Buzz
IO.puts("1, 0, 1")
IO.puts(fizzbuzz.(1, 0, 1))

# 마지막 인자
IO.puts("1, 0, 0")
IO.puts(fizzbuzz.(1, 0, 0))

IO.puts("0, 1, 0")
IO.puts(fizzbuzz.(0, 1, 0))

IO.puts("1, 1, 0")
IO.puts(fizzbuzz.(1, 1, 0))

IO.puts("1, 1, 1")
IO.puts(fizzbuzz.(1, 1, 1))

IO.puts("1, 1, \"Hello World\"")
IO.puts(fizzbuzz.(1, 1, "Hello World"))

fizzbuzztest = fn
  n -> fizzbuzz.(rem(n, 3), rem(n, 5), n)
end

IO.puts("\n=== fizzbuzztest ===\n")
IO.puts(fizzbuzztest.(10))
IO.puts(fizzbuzztest.(11))
IO.puts(fizzbuzztest.(12))
IO.puts(fizzbuzztest.(13))
IO.puts(fizzbuzztest.(14))
IO.puts(fizzbuzztest.(15))
IO.puts(fizzbuzztest.(16))
