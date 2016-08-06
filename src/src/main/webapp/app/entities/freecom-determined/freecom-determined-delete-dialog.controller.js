(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_determinedDeleteController',Freecom_determinedDeleteController);

    Freecom_determinedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_determined'];

    function Freecom_determinedDeleteController($uibModalInstance, entity, Freecom_determined) {
        var vm = this;
        vm.freecom_determined = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_determined.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
