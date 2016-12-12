(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataacquiringcopscDeleteController',Freecom_dataacquiringcopscDeleteController);

    Freecom_dataacquiringcopscDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_dataacquiringcopsc'];

    function Freecom_dataacquiringcopscDeleteController($uibModalInstance, entity, Freecom_dataacquiringcopsc) {
        var vm = this;
        vm.freecom_dataacquiringcopsc = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_dataacquiringcopsc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
