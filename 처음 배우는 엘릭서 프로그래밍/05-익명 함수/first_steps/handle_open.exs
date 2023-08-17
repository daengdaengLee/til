handle_open = fn
  {:ok, file} -> "First line: #{IO.read(file, :line)}"
  {_, error} -> "Error: #{:file.format_error(error)}"
end

# 존재하는 파일을 연다.
IO.puts(handle_open.(File.open("./sample")))
# 존재하지 않는 파일을 연다.
IO.puts(handle_open.(File.open("./nonexistent")))
