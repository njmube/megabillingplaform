(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Public_notaries_federal_entityDetailController', Public_notaries_federal_entityDetailController);

    Public_notaries_federal_entityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Public_notaries_federal_entity'];

    function Public_notaries_federal_entityDetailController($scope, $rootScope, $stateParams, entity, Public_notaries_federal_entity) {
        var vm = this;
        vm.public_notaries_federal_entity = entity;
        vm.load = function (id) {
            Public_notaries_federal_entity.get({id: id}, function(result) {
                vm.public_notaries_federal_entity = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:public_notaries_federal_entityUpdate', function(event, result) {
            vm.public_notaries_federal_entity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
