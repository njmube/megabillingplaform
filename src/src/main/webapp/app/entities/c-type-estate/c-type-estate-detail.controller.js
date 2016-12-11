(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_type_estateDetailController', C_type_estateDetailController);

    C_type_estateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_type_estate'];

    function C_type_estateDetailController($scope, $rootScope, $stateParams, entity, C_type_estate) {
        var vm = this;
        vm.c_type_estate = entity;
        vm.load = function (id) {
            C_type_estate.get({id: id}, function(result) {
                vm.c_type_estate = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_type_estateUpdate', function(event, result) {
            vm.c_type_estate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
