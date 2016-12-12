(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataunacquiringDeleteController',Freecom_dataunacquiringDeleteController);

    Freecom_dataunacquiringDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_dataunacquiring'];

    function Freecom_dataunacquiringDeleteController($uibModalInstance, entity, Freecom_dataunacquiring) {
        var vm = this;
        vm.freecom_dataunacquiring = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_dataunacquiring.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
