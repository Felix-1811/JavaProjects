<script setup lang="ts">
import { ref, computed } from 'vue'
import { characters } from './assets/characters'

const showCharacters = ref(true)
const searchTerm = ref('')
const selectedStatus = ref('')
const selectedGender = ref('')
const sortOrder = ref('az')
const selectedCharacter = ref<any>(null)

const filteredCharacters = computed(() => {
  let result = characters.results

  // Suche
  if (searchTerm.value.trim() !== '') {
    result = result.filter((character) =>
      character.name.toLowerCase().includes(searchTerm.value.toLowerCase())
    )
  }

  // Status
  if (selectedStatus.value !== '') {
    result = result.filter((character) =>
      character.status === selectedStatus.value
    )
  }

  // Gender
  if (selectedGender.value !== '') {
    result = result.filter((character) =>
      character.gender === selectedGender.value
    )
  }

  // Sortierung
  result = [...result]
  if (sortOrder.value === 'az') {
    result.sort((a, b) => a.name.localeCompare(b.name))
  } else {
    result.sort((a, b) => b.name.localeCompare(a.name))
  }

  return result
})

function selectCharacter(character: any) {
  selectedCharacter.value = character
}

function closeDetails() {
  selectedCharacter.value = null
}
</script>

<template>
  <div class="container mt-4">
    <button class="btn btn-primary mb-4" @click="showCharacters = !showCharacters">
      {{ showCharacters ? 'Hide Characters' : 'Show Characters' }}
    </button>

    <h2 class="mb-4">
      Wird ein Character ausgewählt so werden Detailinformationen daneben angezeigt:
    </h2>

    <div v-if="showCharacters">
      <div class="row mb-3">
        <div class="col-md-4 mb-2">
          <input
            v-model="searchTerm"
            type="text"
            class="form-control"
            placeholder="🔍 Search by name..."
          />
        </div>

        <div class="col-md-3 mb-2">
          <select v-model="selectedStatus" class="form-select">
            <option value="">All Status</option>
            <option value="Alive">Alive</option>
            <option value="Dead">Dead</option>
            <option value="unknown">Unknown</option>
          </select>
        </div>

        <div class="col-md-3 mb-2">
          <select v-model="selectedGender" class="form-select">
            <option value="">All Gender</option>
            <option value="Female">Female</option>
            <option value="Male">Male</option>
            <option value="Genderless">Genderless</option>
            <option value="unknown">Unknown</option>
          </select>
        </div>

        <div class="col-md-2 mb-2">
          <button class="btn btn-outline-secondary w-100" @click="sortOrder = sortOrder === 'az' ? 'za' : 'az'">
            Sort {{ sortOrder === 'az' ? 'A–Z' : 'Z–A' }}
          </button>
        </div>
      </div>

      <p class="text-muted">
        Showing {{ filteredCharacters.length }} of {{ characters.info.count }} characters
      </p>

      <div class="row">
        <!-- LINKE SEITE -->
        <div class="col-lg-6">
          <table class="table align-middle">
            <thead class="table-dark">
              <tr>
                <th>Name</th>
                <th>Gender</th>
                <th>Status</th>
                <th>Species</th>
                <th>Image</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="character in filteredCharacters"
                :key="character.id"
                @click="selectCharacter(character)"
                :class="{ selectedRow: selectedCharacter && selectedCharacter.id === character.id }"
                style="cursor: pointer"
              >
                <td>{{ character.name }}</td>
                <td>{{ character.gender }}</td>
                <td>
                  <span class="badge bg-success" v-if="character.status === 'Alive'">
                    Alive
                  </span>
                  <span class="badge bg-danger" v-else-if="character.status === 'Dead'">
                    Dead
                  </span>
                  <span class="badge bg-secondary" v-else>
                    {{ character.status }}
                  </span>
                </td>
                <td>{{ character.species }}</td>
                <td>
                  <img
                    :src="character.image"
                    :alt="character.name"
                    width="55"
                    class="rounded"
                  />
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- RECHTE SEITE -->
        <div class="col-lg-6" v-if="selectedCharacter">
          <div class="card shadow-sm detail-card">
            <div class="card-body">
              <div class="row">
                <div class="col-4">
                  <img
                    :src="selectedCharacter.image"
                    :alt="selectedCharacter.name"
                    class="img-fluid rounded"
                  />
                </div>

                <div class="col-8">
                  <h3>{{ selectedCharacter.name }}</h3>

                  <div class="row">
                    <div class="col-6">
                      <p class="mb-1"><strong>Status:</strong> {{ selectedCharacter.status }}</p>
                      <p class="mb-1"><strong>Species:</strong> {{ selectedCharacter.species }}</p>
                      <p class="mb-1"><strong>Gender:</strong> {{ selectedCharacter.gender }}</p>
                      <p class="mb-1"><strong>Type:</strong> {{ selectedCharacter.type || 'N/A' }}</p>
                    </div>

                    <div class="col-6">
                      <p class="mb-1"><strong>Origin:</strong> {{ selectedCharacter.origin.name }}</p>
                      <p class="mb-1"><strong>Location:</strong> {{ selectedCharacter.location.name }}</p>
                      <p class="mb-1"><strong>Episodes:</strong> {{ selectedCharacter.episode.length }} appearances</p>
                    </div>
                  </div>

                  <button class="btn btn-secondary btn-sm mt-2" @click="closeDetails">
                    ✕ Close
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
.selectedRow {
  background-color: #e9ecef;
  border-left: 3px solid #0d6efd;
}

.detail-card {
  margin-top: 8px;
}
</style>