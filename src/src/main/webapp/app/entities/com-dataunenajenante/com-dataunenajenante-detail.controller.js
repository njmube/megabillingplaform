(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataunenajenanteDetailController', Com_dataunenajenanteDetailController);

    Com_dataunenajenanteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_dataunenajenante', 'Com_data_enajenante'];

    function Com_dataunenajenanteDetailController($scope, $rootScope, $stateParams, entity, Com_dataunenajenante, Com_data_enajenante) {
        var vm = this;
        vm.com_dataunenajenante = entity;
        vm.load = function (id) {
            Com_dataunenajenante.get({id: id}, function(result) {
                vm.com_dataunenajenante = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_dataunenajenanteUpdate', function(event, result) {
            vm.com_dataunenajenante = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
