(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_partial_construction_servicesDialogController', Freecom_partial_construction_servicesDialogController);

    Freecom_partial_construction_servicesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_partial_construction_services', 'Free_cfdi', 'C_federal_entity', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Freecom_partial_construction_servicesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_partial_construction_services, Free_cfdi, C_federal_entity, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.freecom_partial_construction_services = entity;
        vm.clear = clear;
        vm.save = save;
        vm.free_cfdis = Free_cfdi.query();
        vm.c_federal_entities = C_federal_entity.query();
        vm.c_municipalities = C_municipality.query();
        vm.c_colonies = C_colony.query();
        vm.c_zip_codes = C_zip_code.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_partial_construction_services.id !== null) {
                Freecom_partial_construction_services.update(vm.freecom_partial_construction_services, onSaveSuccess, onSaveError);
            } else {
                Freecom_partial_construction_services.save(vm.freecom_partial_construction_services, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_partial_construction_servicesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
