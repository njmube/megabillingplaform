(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_commodityDialogController', Freecom_commodityDialogController);

    Freecom_commodityDialogController.$inject = ['$uibModal','$uibModalInstance', 'entity', 'Freecom_tariff_fraction', 'Freecom_custom_unit'];

    function Freecom_commodityDialogController ($uibModal, $uibModalInstance, entity, Freecom_tariff_fraction, Freecom_custom_unit) {
        var vm = this;

        vm.freecom_commodity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_tariff_fractions = Freecom_tariff_fraction.query({pg: -1});
        vm.freecom_custom_units = Freecom_custom_unit.query();

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.specific_descriptions = [];

        vm.addFreecomSpecificDescriptions = function(){
            $uibModal.open({
                templateUrl: 'app/entities/freecom-specific-descriptions/freecom-specific-descriptions-dialog.html',
                controller: 'Freecom_specific_descriptionsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: '',
                resolve: {
                    entity: function () {
                        return {
                            brand: null,
                            model: null,
                            submodel: null,
                            serial_number: null,
                            id: null
                        };
                    }
                }
            }).result.then(function(result) {
                vm.specific_descriptions.push(result);
            }, function() {
                //do not nothing
            });
        };

        vm.removeFreecomSpecificDescriptions = function(index){
            vm.specific_descriptions.splice(index,1);
        };

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                freecom_commodity: vm.freecom_commodity,
                specific_descriptions: vm.specific_descriptions
            });
            vm.isSaving = false;
        }
    }
})();
