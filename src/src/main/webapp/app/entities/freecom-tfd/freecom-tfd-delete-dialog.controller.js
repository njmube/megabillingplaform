(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tfdDeleteController',Freecom_tfdDeleteController);

    Freecom_tfdDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_tfd'];

    function Freecom_tfdDeleteController($uibModalInstance, entity, Freecom_tfd) {
        var vm = this;
        vm.freecom_tfd = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_tfd.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
