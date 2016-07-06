(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_colonyDetailController', C_colonyDetailController);

    C_colonyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'C_colony', 'C_municipality', 'C_zip_code'];

    function C_colonyDetailController($scope, $rootScope, $stateParams, entity, C_colony, C_municipality, C_zip_code) {
        var vm = this;
        vm.c_colony = entity;
        vm.load = function (id) {
            C_colony.get({id: id}, function(result) {
                vm.c_colony = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:c_colonyUpdate', function(event, result) {
            vm.c_colony = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
