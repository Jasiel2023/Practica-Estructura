import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, NumberField, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { ArtistaService, CancionServices, TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import Cancion from 'Frontend/generated/com/estructura/clase/base/models/Cancion';
import { useCallback, useEffect, useState } from 'react';
import Expresion from 'Frontend/generated/com/estructura/clase/base/models/Expresion';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Cancion',
  },
};


type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};


function CancionEntryForm(props: CancionEntryFormProps) {
  const nombre = useSignal('');
  const genero = useSignal('');
  const album = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');

  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
        const id_genero = parseInt(genero.value) + 1;
        const id_album = parseInt(album.value) + 1;
        await CancionServices.create(nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }

        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumGenero().then(data =>
      //console.log(data)
      listaGenero.value = data
    );
  }, []);
  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumCombo().then(data =>
      //console.log(data)
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listTipo().then(data =>
      //console.log(data)
      listaTipo.value = (data ?? []).filter((item): item is string => typeof item === 'string')
    );
  }, []);
  const dialogOpened = useSignal(false);
  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={createCancion} theme="primary">
              Registrar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre del cancion"
            placeholder="Ingrese el nombre de la cancion"
            aria-label="Nombre del cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox label="Genero"
            items={listaGenero.value}
            itemValuePath="value"
            itemLabelPath="label"
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
          />
          <ComboBox label="Album"
            items={listaAlbum.value}
            placeholder='Seleccione un album'
            aria-label='Seleccione un album de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
          />
          <ComboBox label="Tipo"
            items={listaTipo.value}
            placeholder='Seleccione un tipo de archivo'
            aria-label='Seleccione un tipo de archivo de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <TextField
            label="Duración(s)"
            placeholder="Ingrese la duración de la canción en segundos"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="Link de la cancion"
            placeholder="Ingrese el link de la cancion"
            aria-label="Nombre el link de la cancion"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Agregar
      </Button>
    </>
  );
}

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});



// Formulario para actualizar canciones
type CancionEntryFormUpdateProps = {
  
  id: number;
  nombre: string;
  id_genero: string;
  duracion: number;
  url: string;
  tipo: string;
  id_album: string;
  onCancionUpdated?: () => void;
};

function CancionEntryFormUpdate(props: CancionEntryFormUpdateProps) {
  const dialogOpened = useSignal(false);
  const nombre = useSignal(props.nombre || '');
  const genero = useSignal(props.id_genero);
  const album = useSignal(props.id_album);
  const duracion = useSignal(props.duracion || '');
  const url = useSignal(props.url || '');
  const tipo = useSignal(props.tipo || '');

 

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const updateCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
        const id_genero = parseInt(genero.value);
        const id_album = parseInt(album.value);
        await CancionServices.update(
          props.id,
          nombre.value,
          id_genero,
          parseInt(duracion.value),
          url.value,
          tipo.value,
          id_album
        );
        if (props.onCancionUpdated) {
          props.onCancionUpdated();
        }
        dialogOpened.value = false;
        Notification.show('Canción actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumGenero()
      .then(data => {
        console.log('Géneros cargados:', data);
        listaGenero.value = data;
      })
      .catch(error => console.error('Error al cargar géneros:', error));
  }, []);

  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listaAlbumCombo().then(data => (listaAlbum.value = data));
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionServices.listTipo().then(data => (listaTipo.value = data));
  }, []);


  return (
    <>
      <Dialog
        modeless
        headerTitle="Actualizar canción"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button onClick={updateCancion} theme="primary">
              Actualizar
            </Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre de la canción"
            placeholder="Ingrese el nombre de la canción"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox
            label="Género"
            items={listaGenero.value}
            itemValuePath="value"
            itemLabelPath="label"
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
            placeholder="Seleccione un género"
            required
          />
          <ComboBox
            label="Álbum"
            items={listaAlbum.value}
            itemValuePath="value"
            itemLabelPath="label"
            placeholder="Seleccione un álbum"
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
          />
          <ComboBox
            label="Tipo"
            items={listaTipo.value}
            placeholder="Seleccione un tipo de archivo"
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <NumberField
            label="Duración"
            placeholder="Ingrese la duración de la canción"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField
            label="Link de la canción"
            placeholder="Ingrese el link de la canción"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}





function index({ model }: { model: GridItemModel<Expresion> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

// Actualizar la lista de canciones
export default function CancionView() {
  const [items, setItems] = useState([]);
  const callData = () => {
    console.log("Hola call data");
    CancionServices.listAll().then(function(data){
      //items.values = data;
      setItems(data);
    });
  };
  useEffect(() => {
    callData();
  },[]);

  /*const [items, setItems] = useState([]);
  useEffect(() => {
    CancionServices.listAll().then(function (data) {
      //items.values = data;
      setItems(data);
    });
  }, []);*/


  /*const dataProvider = useDataProvider<Cancion>({
    list: () => CancionServices.listAll(),
  });*/

function link({ item }: { item: Cancion }) {
  return (
    <span>
      <CancionEntryFormUpdate
        id={item.id}
        nombre={item.nombre}
        id_genero={item.id_genero}
        duracion={item.duracion}
        url={item.url}
        tipo={item.tipo}
        id_album={item.id_album}
        arguments={item}
        onCancionUpdated={callData}
      />
    </span>
  );
}




  const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    // Custom logic based on the sorting direction
    console.log(`Sort direction changed for column ${columnId} to ${direction}`);

    var dir = (direction == 'asc') ? 1 : 2;
    CancionServices.order(columnId, dir).then(function (data) {
      setItems(data);
    });
  }


  // PARA BUSCAR

  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Nombre',
      value: 'nombre',
    },
    {
      label: 'Genero',
      value: 'genero',
    },
    {
      label: 'Album',
      value: 'album',
    },
    {
      label: 'Tipo',
      value: 'tipo',
    },
  ];
  const search = async () => {

    try {
      if (!criterio.value && !texto.value) {
      CancionServices.listAll().then(function (data) {
        setItems(data);
      });
      Notification.show('Lista refrescada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      return;
    }

      console.log(criterio.value + " " + texto.value);
      CancionServices.search(criterio.value, texto.value, 0).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });


    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Canciones">
        <Group>
          <CancionEntryForm onCancionCreated={callData}/>
        </Group>
      </ViewToolbar>
      <HorizontalLayout theme="spacing">
        <Select items={itemSelect}
          value={criterio.value}
          onValueChanged={(evt) => (criterio.value = evt.detail.value)}
          placeholder="Selecione un cirterio">


        </Select>

        <TextField
          placeholder="Search"
          style={{ width: '50%' }}
          value={texto.value}
          onValueChanged={(evt) => (texto.value = evt.detail.value)}
        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          BUSCAR
        </Button>
      </HorizontalLayout>
      <Grid items={items}>
        <GridColumn header="Nro" renderer={index} />
        <GridSortColumn onDirectionChanged={(e) => order(e, "nombre")} path="nombre" header="Nombre" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "genero")} path="genero" header="Genero" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "album")} path="album" header="Album" />
        <GridSortColumn onDirectionChanged={(e) => order(e, "tipo")} path="tipo" header="Tipo" />
        <GridColumn path="duracion" header="Duracion"/>
        <GridColumn path="url" header="Link " />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}



/*
  function indexLink({ item }: { item: Cancion }) {

    return (
      <span>
        <CancionEntryFormUpdate  
        id={item.id}
        nombre={item.nombre}
        id_genero={parseInt(item.id_genero)}
        duracion={parseInt(item.duracion)}
        url={item.url}
        tipo={item.tipo} 
        id_album={item.id_album}
        onCancionUpdated={callData}
        />
      </span>
    );
  }

  function indexIndex({ model }: { model: GridItemModel<Cancion> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Lista de canciones">
        <Group>
          <CancionEntryForm  onCancionCreated={callData} />
        </Group>
      </ViewToolbar>
           <Grid items={items}>
        <GridColumn renderer={indexIndex} header="Nro" />
        <GridSortColumn path="nombre" header="Nombre de la cancion" onDirectionChanged={(e) => order(e, 'nombre')} />
        <GridColumn path="genero" header="Género" />
        <GridColumn path="tipo" header="Tipo" />
        <GridColumn header="Acciones" renderer={indexLink} />
      </Grid>
    </main>
  );
}
*/