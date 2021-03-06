(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_pn_federal_entityDetailController', C_pn_federal_entityDetailController);

    C_pn_federal_entityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_pn_federal_entity'];

    function C_pn_federal_entityDetailController($scope, $rootScope, $stateParams, entity, C_pn_federal_entity) {
        var vm = this;
        vm.c_pn_federal_entity = entity;
        vm.load = function (id) {
            C_pn_federal_entity.get({id: id}, function(result) {
                vm.c_pn_federal_entity = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_pn_federal_entityUpdate', function(event, result) {
            vm.c_pn_federal_entity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
