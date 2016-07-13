(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Entity_cfdiDetailController', Entity_cfdiDetailController);

    Entity_cfdiDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Entity_cfdi', 'Accounting', 'Key_entity', 'Scope', 'Freecom_ine'];

    function Entity_cfdiDetailController($scope, $rootScope, $stateParams, entity, Entity_cfdi, Accounting, Key_entity, Scope, Freecom_ine) {
        var vm = this;
        vm.entity_cfdi = entity;
        vm.load = function (id) {
            Entity_cfdi.get({id: id}, function(result) {
                vm.entity_cfdi = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:entity_cfdiUpdate', function(event, result) {
            vm.entity_cfdi = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
