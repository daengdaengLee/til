IO.puts("1번 문제")
IO.inspect(Enum.map([1, 2, 3, 4], fn x -> x + 2 end))
IO.inspect(Enum.map([1, 2, 3, 4], &(&1 + 2)))

IO.puts("2번 문제")
Enum.each([1, 2, 3, 4], fn x -> IO.inspect(x) end)
Enum.each([1, 2, 3, 4], &IO.inspect/1)
