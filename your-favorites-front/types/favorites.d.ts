export type Favorite = {
  title: string
  desc: string
} & ({ children: Favorite[] } | { url: string })

export type Favorites = Favorite[]
