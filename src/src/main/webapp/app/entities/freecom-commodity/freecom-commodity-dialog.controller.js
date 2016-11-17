(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_commodityDialogController', Freecom_commodityDialogController);

    Freecom_commodityDialogController.$inject = ['$uibModal','$uibModalInstance', 'entity', 'freecom_specific_descriptions_entity', 'Freecom_tariff_fraction', 'Freecom_custom_unit'];

    function Freecom_commodityDialogController ($uibModal, $uibModalInstance, entity, freecom_specific_descriptions_entity, Freecom_tariff_fraction, Freecom_custom_unit) {
        var vm = this;

        vm.freecom_commodity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.freecom_tariff_fractions = Freecom_tariff_fraction.query();
        vm.freecom_custom_units = Freecom_custom_unit.query();

        vm.use_specific_descriptions = false;
        vm.freecom_specific_descriptions = freecom_specific_descriptions_entity;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                freecom_commodity: vm.freecom_commodity,
                specific_descriptions: vm.freecom_specific_descriptions
            });
            vm.isSaving = false;
        }
    }
})();
