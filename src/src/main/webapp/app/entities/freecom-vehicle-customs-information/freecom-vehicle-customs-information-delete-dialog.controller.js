(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_vehicle_customs_informationDeleteController',Freecom_vehicle_customs_informationDeleteController);

    Freecom_vehicle_customs_informationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_vehicle_customs_information'];

    function Freecom_vehicle_customs_informationDeleteController($uibModalInstance, entity, Freecom_vehicle_customs_information) {
        var vm = this;
        vm.freecom_vehicle_customs_information = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_vehicle_customs_information.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
