(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_commodityDialogController', Com_commodityDialogController);

    Com_commodityDialogController.$inject = ['$uibModal','$uibModalInstance', 'entity', 'com_specific_descriptions_entity', 'Com_tariff_fraction', 'Com_custom_unit'];

    function Com_commodityDialogController ($uibModal, $uibModalInstance, entity, com_specific_descriptions_entity, Com_tariff_fraction, Com_custom_unit) {
        var vm = this;

        vm.com_commodity = entity;
        vm.clear = clear;
        vm.save = save;
        vm.com_tariff_fractions = Com_tariff_fraction.query();
        vm.com_custom_units = Com_custom_unit.query();

        vm.use_specific_descriptions = false;
        vm.com_specific_descriptions = com_specific_descriptions_entity;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            $uibModalInstance.close({
                com_commodity: vm.com_commodity,
                specific_descriptions: vm.com_specific_descriptions
            });
            vm.isSaving = false;
        }
    }
})();
