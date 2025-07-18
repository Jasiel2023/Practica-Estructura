import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextArea, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Task from 'Frontend/generated/com/estructura/clase/taskmanagement/domain/Task';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useCallback, useEffect, useState } from 'react';


import { BandaService } from 'Frontend/generated/endpoints';
import Banda from 'Frontend/generated/com/estructura/clase/base/models/Banda';
import { data } from 'react-router';

export const config: ViewConfig = {
  title: 'Banda',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Banda',
  },
};

type BandaEntryFormProps = {
  onBandaCreated?: () => void;
};

type BandaEntryFormUpdateProps =  {
  onBandaUpdated?: () => void;
};
//BANDA CREATE
function BandaEntryForm(props: BandaEntryFormProps) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal('');
  const fecha = useSignal('');

  const createBanda = async () => {
      try {
        if (nombre.value.trim().length > 0 && fecha.value.trim().length > 0) {
          await BandaService.createBanda(nombre.value, fecha.value);
          if (props.onBandaCreated) {
            props.onBandaCreated();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Banda creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
        } else {
          Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
        }
  
      } catch (error) {
        console.log(error);
        handleError(error);
      }
    };

  return (
    <>
      <Dialog
        aria-label="Registrar Banda"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Banda
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createBanda}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la banda'
              aria-label='Ingrese el nombre de la banda'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <DatePicker
              label="Fecha de creacion"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//UPDATE BANDA
function BandaEntryFormUpdate(props: BandaEntryFormUpdateProps) {
  //console.log(props);
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const fecha = useSignal(props.arguments.fecha);
  const ident = useSignal(props.arguments.id);
  
  const updateBanda = async () => {
      try {
        if (nombre.value.trim().length > 0 && fecha.value.trim().length > 0) {
          console.log(parseInt(ident.value)+" *********");
          await BandaService.updateBanda(parseInt(ident.value), nombre.value, fecha.value);
          if (props.onBandaUpdated) {
            props.onBandaUpdated();
          }
          nombre.value = '';
          fecha.value = '';
          dialogOpened.value = false;
          Notification.show('Banda creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
        } else {
          Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
        }
  
      } catch (error) {
        console.log(error);
        handleError(error);
      }
    };

  return (
    <>
      <Dialog
        aria-label="Editar Banda"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Banda
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateBanda}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la banda'
              aria-label='Ingrese el nombre de la banda'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <DatePicker
              label="Fecha de creacion"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}
//*************************** */

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});



function index({ model }: { model: GridItemModel<Banda> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}

export default function BandaListView() {
  const dataProvider = useDataProvider<Banda>({
    list: () => BandaService.listAllBanda(),
  });

  function link({ item }: { item: Banda }) {
  return (
    <span>
      <BandaEntryFormUpdate arguments={item}  onBandaUpdated={dataProvider.refresh}/>
    </span>
  );
}

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Bandas">
        <Group>
          <BandaEntryForm onBandaCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Nro" renderer={index} />
        <GridColumn path="nombre" header="Nombre del artista" />
        <GridColumn path="fecha" header="Fecha">
          {({ item }) => (item.dueDate ? dateFormatter.format(new Date(item.dueDate)) : 'Never')}
        </GridColumn>
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}
