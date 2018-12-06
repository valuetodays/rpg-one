package billy.rpg.game.core.buff;

public enum BuffType {
      ENHANCE_ATTACK {
          @Override
          public String toString() {
              return "攻升";
          }

          @Override
          public BuffType getOpposite() {
              return WEAKEN_ATTACK;
          }
      }, WEAKEN_ATTACK {
        @Override
        public String toString() {
            return "攻降";
        }

        @Override
        public BuffType getOpposite() {
            return ENHANCE_ATTACK;
        }
    }, ENHANCE_DEFEND {
        @Override
        public String toString() {
            return "防升";
        }

        @Override
        public BuffType getOpposite() {
            return WEAKEN_DEFEND;
        }
    }, WEAKEN_DEFEND {
        @Override
        public String toString() {
            return "防降";
        }

        @Override
        public BuffType getOpposite() {
            return ENHANCE_DEFEND;
        }
    }, ENHANCE_SPEED {
        @Override
        public String toString() {
            return "速升";
        }

        @Override
        public BuffType getOpposite() {
            return WEAKEN_SPEED;
        }
    }, WEAKEN_SPEED {
        @Override
        public String toString() {
            return "速降";
        }

        @Override
        public BuffType getOpposite() {
            return ENHANCE_SPEED;
        }
    }, DU {
        @Override
        public String toString() {
            return "+毒";
        }
        @Override
        public BuffType getOpposite() {
            return REMOVE_DU;
        }
    }, REMOVE_DU {
        @Override
        public String toString() {
            return "-毒";
        }
        @Override
        public BuffType getOpposite() {
            return DU;
        }
    }, LUAN {
        @Override
        public String toString() {
            return "+乱";
        }
        @Override
        public BuffType getOpposite() {
            return REMOVE_LUAN;
        }
    }, REMOVE_LUAN {
        @Override
        public String toString() {
            return "-乱";
        }

        @Override
        public BuffType getOpposite() {
            return LUAN;
        }
    }, FENG {
        @Override
        public String toString() {
            return "+封";
        }

        @Override
        public BuffType getOpposite() {
            return REMOVE_FENG;
        }
    }, REMOVE_FENG {
        @Override
        public String toString() {
            return "-封";
        }
        @Override
        public BuffType getOpposite() {
            return FENG;
        }
    }, MIAN {
        @Override
        public String toString() {
            return "+眠";
        }
        @Override
        public BuffType getOpposite() {
            return REMOVE_MIAN;
        }
    }, REMOVE_MIAN {
        @Override
        public String toString() {
            return "-眠";
        }
        @Override
        public BuffType getOpposite() {
            return MIAN;
        }
    };

    public boolean isOpposite(BuffType theOther) {
        return theOther == getOpposite();
    }

    public abstract BuffType getOpposite();
}
