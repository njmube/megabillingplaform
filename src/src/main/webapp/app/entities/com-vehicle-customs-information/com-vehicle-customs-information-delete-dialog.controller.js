(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_vehicle_customs_informationDeleteController',Com_vehicle_customs_informationDeleteController);

    Com_vehicle_customs_informationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_vehicle_customs_information'];

    function Com_vehicle_customs_informationDeleteController($uibModalInstance, entity, Com_vehicle_customs_information) {
        var vm = this;
        vm.com_vehicle_customs_information = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_vehicle_customs_information.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
