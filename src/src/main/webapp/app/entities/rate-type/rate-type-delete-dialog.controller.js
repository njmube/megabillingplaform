(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Rate_typeDeleteController',Rate_typeDeleteController);

    Rate_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rate_type'];

    function Rate_typeDeleteController($uibModalInstance, entity, Rate_type) {
        var vm = this;
        vm.rate_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Rate_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
