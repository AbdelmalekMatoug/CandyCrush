
library ieee;
use ieee.std_logic_1164.all;

entity tb_Fulladder is
end tb_Fulladder;

architecture tb of tb_Fulladder is

    component Fulladder
        port (A    : in std_logic;
              B    : in std_logic;
              S    : out std_logic;
              Cin  : in std_logic;
              Cout : out std_logic);
    end component;

    signal A    : std_logic;
    signal B    : std_logic;
    signal S    : std_logic;
    signal Cin  : std_logic;
    signal Cout : std_logic;

begin

    dut : Fulladder
    port map (A    => A,
              B    => B,
              S    => S,
              Cin  => Cin,
              Cout => Cout);

    stimuli : process
    begin

        A <= '0';
        B <= '1';
        Cin <= '0';


        wait;
    end process;

end tb;


